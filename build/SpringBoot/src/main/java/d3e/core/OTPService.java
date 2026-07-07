package d3e.core;

import classes.MutateResultStatus;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.BaseUser;
import models.EmailMessage;
import models.OneTimePassword;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.UserRepository;
import store.EntityMutator;
import store.ValidationFailedException;

@Service
public class OTPService {
  private static OTPService instance;
  @Autowired private UserRepository userRepository;
  @Autowired private EntityMutator mutator;
  @Autowired private EmailService emailService;

  @PostConstruct
  public void init() {
    instance = this;
  }

  public static OTPService get() {
    return instance;
  }

  public void create(OneTimePassword otp) {
    String userType = otp.getUserType();
    switch (userType) {
      case "User":
        {
          getUserAndProceed(otp);
          break;
        }
      default:
        {
          throw new ValidationFailedException(
              MutateResultStatus.BadRequest, ListExt.asList("Invalid user type: " + userType));
        }
    }
  }

  private void getUserAndProceed(OneTimePassword otp) {
    String inputType = otp.getInputType();
    String input = otp.getInput();
    switch (inputType) {
      case "email":
        {
          User user = userRepository.getByEmail(input.toLowerCase());
          if (!(populate(user, otp, false))) {
            return;
          }
          sendEmailToUser(otp, user);
          break;
        }
      default:
        {
          throw new ValidationFailedException(
              MutateResultStatus.BadRequest, ListExt.asList("Invalid input type: " + inputType));
        }
    }
  }

  private void sendEmailToUser(OneTimePassword otp, User user) {
    String email = user.getEmail();
    StringTemplate forSubject = StringTemplate.fromString("One Time Password");
    forSubject.put("user", user);
    forSubject.put("otp", otp);
    StringTemplate forBody =
        StringTemplate.fromString("Your OTP is: $!{otp.code} It is valid for 10 minutes.");
    forBody.put("user", user);
    forBody.put("otp", otp);
    sendEmail(email, forSubject.merge(), forBody.merge());
  }

  private void sendEmail(String sendTo, String subject, String body) {
    EmailMessage msg = new EmailMessage();
    msg.setTo(ListExt.asList(sendTo));
    msg.setSubject(subject);
    msg.setBody(body);
    this.emailService.send(msg);
  }

  private boolean populate(BaseUser user, OneTimePassword otp, boolean isDemoUser) {
    if (user == null) {
      failure(otp);
      return false;
    }
    success(user, otp, isDemoUser);
    return true;
  }

  private void failure(OneTimePassword otp) {
    List<String> errors = new ArrayList();
    otp.setSuccess(false);
    otp.setErrorMsg("Invalid authentication details.");
    errors.add("Invalid authentication details.");
    otp.setToken("");
    otp.setCode("");
    throw new ValidationFailedException(MutateResultStatus.AuthFail, errors);
  }

  private void success(BaseUser user, OneTimePassword otp, boolean isDemo) {
    otp.setUser(user);
    otp.setSuccess(true);
    otp.setErrorMsg("");
    otp.setToken(generateToken());
    otp.setCode(isDemo ? generateDemoCode() : generateCode());
    otp.setExpiry(LocalDateTime.now().plusMinutes(10));
  }

  private String generateToken() {
    char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    return generateRandomString(chars, 32);
  }

  private String generateDemoCode() {
    String digits = "1234567890";
    return digits.substring(0, 4);
  }

  private String generateCode() {
    char[] digits = "1234567890".toCharArray();
    return generateRandomString(digits, 4);
  }

  private String generateRandomString(char[] array, int length) {
    StringBuilder sb = new StringBuilder(length);
    Random rnd = new Random();
    for (int i = 0; i < length; i++) {
      char c = array[rnd.nextInt(array.length)];
      sb.append(c);
    }
    return sb.toString();
  }
}
