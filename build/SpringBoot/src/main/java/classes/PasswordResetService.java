package classes;

import java.time.LocalDateTime;
import models.OneTimePassword;
import models.User;
import repository.jpa.OneTimePasswordRepository;
import repository.jpa.UserRepository;
import store.Database;
import store.EntityMutator;
import store.ValidationFailedException;

public class PasswordResetService {
  private PasswordResetService() {}

  public static LoginResult sendOtp(
      String email, UserRepository userRepository, EntityMutator mutator) {
    LoginResult result = new LoginResult();

    if (email == null || email.trim().isEmpty()) {
      result.setSuccess(false);
      result.setFailureMessage("Please enter your email.");
      return result;
    }

    String normalizedEmail = email.trim().toLowerCase();
    if (userRepository.getByEmail(normalizedEmail) == null) {
      result.setSuccess(false);
      result.setFailureMessage("Invalid authentication details.");
      return result;
    }

    OneTimePassword otp = new OneTimePassword();
    otp.setUserType("User");
    otp.setInputType("email");
    otp.setInput(normalizedEmail);

    try {
      mutator.save(otp, true);
    } catch (ValidationFailedException e) {
      result.setSuccess(false);
      result.setFailureMessage(
          e.getErrors().isEmpty() ? "Unable to send OTP." : e.getErrors().get(0));
      return result;
    }

    if (!otp.isSuccess() || otp.getToken() == null || otp.getToken().isEmpty()) {
      result.setSuccess(false);
      result.setFailureMessage(
          otp.getErrorMsg() != null && !otp.getErrorMsg().isEmpty()
              ? otp.getErrorMsg()
              : "Unable to send OTP.");
      return result;
    }
    result.setSuccess(true);
    result.setToken(otp.getToken());
    result.setFailureMessage("");
    return result;
  }

  public static LoginResult resetPassword(
      String token,
      String code,
      String newPassword,
      OneTimePasswordRepository oneTimePasswordRepository,
      UserRepository userRepository) {
    LoginResult result = new LoginResult();

    if (token == null || token.trim().isEmpty()) {
      result.setSuccess(false);
      result.setFailureMessage("Invalid reset request. Please request a new OTP.");
      return result;
    }

    if (code == null || code.trim().isEmpty()) {
      result.setSuccess(false);
      result.setFailureMessage("Please enter the OTP code.");
      return result;
    }

    if (newPassword == null || newPassword.length() < 6) {
      result.setSuccess(false);
      result.setFailureMessage("Password must be at least 6 characters.");
      return result;
    }

    OneTimePassword otp = oneTimePasswordRepository.getByToken(token.trim());
    if (otp == null) {
      result.setSuccess(false);
      result.setFailureMessage("Invalid or expired OTP. Please request a new one.");
      return result;
    }

    if (otp.getExpiry() == null || otp.getExpiry().isBefore(LocalDateTime.now())) {
      result.setSuccess(false);
      result.setFailureMessage("OTP has expired. Please request a new one.");
      return result;
    }

    if (!code.trim().equals(otp.getCode())) {
      result.setSuccess(false);
      result.setFailureMessage("Invalid OTP code.");
      return result;
    }

    if (!(otp.getUser() instanceof User)) {
      result.setSuccess(false);
      result.setFailureMessage("Invalid reset request.");
      return result;
    }

    User otpUser = (User) otp.getUser();
    User user = userRepository.getByEmail(otpUser.getEmail().toLowerCase());
    if (user == null) {
      result.setSuccess(false);
      result.setFailureMessage("Invalid reset request.");
      return result;
    }
    user.setPassword(newPassword);
    Database.get().save(user);
    Database.get().delete(otp);

    result.setSuccess(true);
    result.setFailureMessage("");
    return result;
  }
}
