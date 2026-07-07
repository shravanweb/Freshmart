package d3e.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import jakarta.annotation.PostConstruct;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;
import jakarta.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import classes.Env;
import d3e.core.DFile;
import models.EmailMessage;
import models.VerificationData;
import store.Database;

@Service
public class EmailService implements Runnable {
  @Autowired
  private Environment env;

  private LinkedBlockingQueue<EmailMessage> emails = new LinkedBlockingQueue<>();
  
  @Autowired
  private TransactionWrapper wrapper;

  @PostConstruct
  public void init() {
    new Thread(this).start();
  }

  public void send(EmailMessage mail) {
    if (mail == null) {
      return;
    }
    pushEmail(mail);
  }

  public void sendNow(EmailMessage mail) throws MessagingException, IOException {
    if (mail == null) {
      return;
    }
    sendEmail(mail);
  }
  
  public void sendVerificationEmail(String email, String context, String body, boolean html, String subject) {
    if (email == null || context == null) {
      return;
    }
    /*
     * Generate token
     */
    String token = UUID.randomUUID().toString();
    
    /*
     * Save VerificationData object
     */
    VerificationData data = new VerificationData();
    data.setMethod(email);
    data.setContext(context);
    data.setToken(token);
    data.setBody(body);
    data.setSubject(subject);
    try {
      wrapper.doInTransaction(() -> {
        Database.get().save(data);
      });
    } catch (ServletException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    /*
     * Generate link
     */
    String link = Env.get().getBaseHttpUrl() + "/verify?code=" + token;
    
    /*
     * Send link via email
     */
    if (body.isEmpty()) {
      body = "This is your verification link: {link}";
    }
    body = StringExt.replaceAll(body, "\\{link\\}", link);
    if (subject.isEmpty()) {
      subject = "Verification link";
    }
    EmailMessage msg = new EmailMessage();
    msg.setTo(ListExt.asList(email));
    msg.setSubject(subject);
    msg.setBody(body);
    msg.setHtml(html);
    send(msg);
  }
  
  @Override
  public void run() {
    while (true) {
      EmailMessage mail = null;
      try {
        mail = emails.take();
        sendEmail(mail);
      } catch (MessagingException | IOException e) {
        if (mail != null) {
          e.printStackTrace(System.err);
        }
      } catch (InterruptedException e) {
      }
    }
  }

  private synchronized void pushEmail(EmailMessage mail) {
    emails.add(mail);
  }

  private String getEnvString(String str) {
    return EnvironmentHelper.getEnvString(env, str);
  }

  private boolean isSmtpConfigured() {
    String username = getEnvString("{env.mail.uname}");
    String password = getEnvString("{env.mail.pwd}");
    if (username == null
        || username.isEmpty()
        || password == null
        || password.isEmpty()) {
      return false;
    }
    String upperUser = username.toUpperCase();
    String upperPwd = password.toUpperCase();
    return !(upperUser.contains("USERNAME_HERE")
        || upperUser.contains("YOUR_")
        || upperPwd.contains("PASSWORD_HERE")
        || upperPwd.contains("YOUR_"));
  }

  private void logDevEmail(EmailMessage email) {
    Log.info("=== DEV EMAIL (SMTP not configured — printing to console) ===");
    Log.info("To: " + String.join(", ", email.getTo()));
    Log.info("Subject: " + email.getSubject());
    List<DFile> attachments = email.getAttachments();
    if (attachments != null && !attachments.isEmpty()) {
      for (DFile attachment : attachments) {
        Log.info(
            "Attachment: "
                + (attachment.getName() != null ? attachment.getName() : "attachment")
                + " ("
                + attachment.getId()
                + ")");
      }
    }
    Log.info("Body: " + email.getBody());
    Log.info("============================================================");
  }

  private void sendEmail(EmailMessage email)
      throws AddressException, MessagingException, IOException {
    if (!isSmtpConfigured()) {
      logDevEmail(email);
      return;
    }

    Properties prop = new Properties();

    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", true);
    prop.put("mail.smtp.host", getEnvString("{env.mail.smtp.host}"));
    prop.put("mail.smtp.port", getEnvString("{env.mail.smtp.port}"));

    String username = getEnvString("{env.mail.uname}");
    String password = getEnvString("{env.mail.pwd}");
    email.setFrom(getEnvString("{env.mail.sender}"));

    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(email.getFrom()));
    setRecipientsIfPresent(message, RecipientType.TO, email.getTo());
    setRecipientsIfPresent(message, RecipientType.CC, email.getCc());
    setRecipientsIfPresent(message, RecipientType.BCC, email.getBcc());
    message.setSubject(email.getSubject());
    List<DFile> attachments = email.getAttachments();
    if (attachments != null && !attachments.isEmpty()) {
      MimeMultipart multipart = new MimeMultipart();
      MimeBodyPart textPart = new MimeBodyPart();
      if (email.isHtml()) {
        textPart.setContent(email.getBody(), "text/html; charset=utf-8");
      } else {
        textPart.setText(email.getBody());
      }
      multipart.addBodyPart(textPart);
      for (DFile attachment : attachments) {
        multipart.addBodyPart(buildAttachmentPart(attachment));
      }
      message.setContent(multipart);
    } else if (email.isHtml()) {
      message.setContent(email.getBody(), "text/html; charset=utf-8");
    } else {
      message.setText(email.getBody());
    }
    Transport.send(message);
  }

  private void setRecipientsIfPresent(Message message, RecipientType type, List<String> addresses)
      throws AddressException, MessagingException {
    if (addresses == null || addresses.isEmpty()) {
      return;
    }
    String joined = String.join(",", addresses).trim();
    if (joined.isEmpty()) {
      return;
    }
    message.setRecipients(type, InternetAddress.parse(joined));
  }

  private MimeBodyPart buildAttachmentPart(DFile attachment) throws MessagingException, IOException {
    MimeBodyPart attachmentPart = new MimeBodyPart();
    Resource resource = FileController.load(attachment);
    byte[] fileBytes;
    try (InputStream inputStream = resource.getInputStream()) {
      fileBytes = inputStream.readAllBytes();
    }
    String mimeType = attachment.getMimeType();
    if (mimeType == null || mimeType.trim().isEmpty()) {
      mimeType = "application/octet-stream";
    }
    attachmentPart.setContent(fileBytes, mimeType);
    String fileName =
        attachment.getName() != null && !attachment.getName().trim().isEmpty()
            ? attachment.getName().trim()
            : "attachment";
    attachmentPart.setFileName(MimeUtility.encodeText(fileName));
    return attachmentPart;
  }
}
