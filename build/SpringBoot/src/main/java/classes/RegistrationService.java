package classes;

import d3e.core.ListExt;
import d3e.core.Services;
import java.util.List;
import java.util.UUID;
import models.EmailMessage;
import models.Organization;
import models.User;
import models.UserProfile;
import org.springframework.security.crypto.password.PasswordEncoder;
import repository.jpa.OrganizationRepository;
import repository.jpa.UserProfileRepository;
import repository.jpa.UserRepository;
import security.JwtTokenUtil;
import security.UserProxy;
import store.Database;

public class RegistrationService {
  private RegistrationService() {}

  public static LoginResult register(
      String displayName,
      String email,
      String password,
      AppUserRole appRole,
      UserRepository userRepository,
      OrganizationRepository organizationRepository,
      PasswordEncoder passwordEncoder,
      JwtTokenUtil jwtTokenUtil) {
    LoginResult loginResult = new LoginResult();

    if (displayName == null || displayName.trim().isEmpty()) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Please enter your name.");
      return loginResult;
    }

    if (email == null || email.trim().isEmpty()) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Please enter your email.");
      return loginResult;
    }

    if (password == null || password.length() < 6) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Password must be at least 6 characters.");
      return loginResult;
    }

    String normalizedEmail = email.trim().toLowerCase();
    if (userRepository.getByEmail(normalizedEmail) != null) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("An account with this email already exists.");
      return loginResult;
    }

    Organization organization = resolveOrganization(organizationRepository);
    AppUserRole selectedRole = resolveAppRole(appRole);

    User user = new User();
    user.setEmail(normalizedEmail);
    user.setPassword(password);
    user.setRole(selectedRole);
    user.setStatus(EntityStatus.Active);
    user.setOrganization(organization);
    Database.get().save(user);

    UserProfile profile = new UserProfile();
    profile.setUser(user);
    profile.setDisplayName(displayName.trim());
    profile.setAppRole(selectedRole);
    profile.setStatus(EntityStatus.Active);
    profile.setOrganization(organization);
    Database.get().save(profile);

    sendWelcomeEmail(normalizedEmail, displayName.trim(), organization.getName());

    loginResult.setSuccess(true);
    loginResult.setUserObject(user);
    loginResult.setToken(
        jwtTokenUtil.generateToken(
            normalizedEmail, new UserProxy("User", user.getId(), UUID.randomUUID().toString())));

    return loginResult;
  }

  public static LoginResult registerStaff(
      User adminUser,
      String displayName,
      String email,
      String password,
      AppUserRole appRole,
      UserRepository userRepository,
      UserProfileRepository userProfileRepository,
      OrganizationRepository organizationRepository,
      PasswordEncoder passwordEncoder) {
    LoginResult loginResult = new LoginResult();

    if (adminUser == null) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("You must be signed in as an admin.");
      return loginResult;
    }

    UserProfile adminProfile = userProfileRepository.getByUser(adminUser);
    if (adminProfile == null || adminProfile.getAppRole() != AppUserRole.OrganizationAdmin) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Only organization admins can create staff accounts.");
      return loginResult;
    }

    if (appRole == null || appRole == AppUserRole.Viewer) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Customer accounts must use public signup.");
      return loginResult;
    }

    if (displayName == null || displayName.trim().isEmpty()) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Please enter a name.");
      return loginResult;
    }

    if (email == null || email.trim().isEmpty()) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Please enter an email.");
      return loginResult;
    }

    if (password == null || password.length() < 6) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Password must be at least 6 characters.");
      return loginResult;
    }

    String normalizedEmail = email.trim().toLowerCase();
    if (userRepository.getByEmail(normalizedEmail) != null) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("An account with this email already exists.");
      return loginResult;
    }

    Organization organization =
        adminProfile.getOrganization() != null
            ? adminProfile.getOrganization()
            : resolveOrganization(organizationRepository);

    User user = new User();
    user.setEmail(normalizedEmail);
    user.setPassword(password);
    user.setRole(appRole);
    user.setStatus(EntityStatus.Active);
    user.setOrganization(organization);
    Database.get().save(user);

    UserProfile profile = new UserProfile();
    profile.setUser(user);
    profile.setDisplayName(displayName.trim());
    profile.setAppRole(appRole);
    profile.setStatus(EntityStatus.Active);
    profile.setOrganization(organization);
    Database.get().save(profile);

    sendWelcomeEmail(normalizedEmail, displayName.trim(), organization.getName());

    loginResult.setSuccess(true);
    loginResult.setUserObject(user);
    return loginResult;
  }

  private static void sendWelcomeEmail(String email, String displayName, String organizationName) {
    try {
      Services services = Services.get();
      if (services == null || services.getEmailService() == null) {
        return;
      }

      String orgLabel =
          organizationName != null && !organizationName.trim().isEmpty()
              ? organizationName.trim()
              : "FreshMart Retail Group";
      String subject = "Welcome to " + orgLabel;
      String body =
          "Hi "
              + displayName
              + ",\n\n"
              + "Your account has been created successfully.\n\n"
              + "You can sign in to the "
              + orgLabel
              + " Inventory Management System using this email address: "
              + email
              + "\n\n"
              + "Thank you,\n"
              + orgLabel
              + " Team";

      EmailMessage message = new EmailMessage();
      message.setTo(ListExt.asList(email));
      message.setSubject(subject);
      message.setBody(body);
      services.getEmailService().send(message);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private static AppUserRole resolveAppRole(AppUserRole appRole) {
    // Public signup always creates customer (Viewer) accounts.
    // Staff roles must be created by an organization admin.
    return AppUserRole.Viewer;
  }

  private static Organization resolveOrganization(OrganizationRepository organizationRepository) {
    Organization organization = organizationRepository.getByCode("FMRG");
    if (organization != null) {
      return organization;
    }

    organization = organizationRepository.getByName("FreshMart Retail Group");
    if (organization != null) {
      return organization;
    }

    List<Organization> organizations = organizationRepository.findAll();
    if (!organizations.isEmpty()) {
      return organizations.get(0);
    }

    Organization defaultOrganization = new Organization();
    defaultOrganization.setName("FreshMart Retail Group");
    defaultOrganization.setCode("FMRG");
    defaultOrganization.setLegalName("FreshMart Retail Group LLC");
    defaultOrganization.setEmail("ops@freshmart.demo");
    defaultOrganization.setCurrency("USD");
    defaultOrganization.setTimezone("UTC");
    defaultOrganization.setStatus(OrganizationStatus.Active);
    return OrganizationService.createOrganization(defaultOrganization);
  }
}
