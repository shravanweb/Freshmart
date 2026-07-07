package rest.ws;

import classes.LoginResult;
import java.util.UUID;
import models.BaseUser;
import models.OneTimePassword;
import models.UserLoginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.jpa.OneTimePasswordRepository;
import security.JwtTokenUtil;
import security.UserProxy;

@Component("loginWithOTP")
public class OtpAuthenticator implements Authenticator {
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private OneTimePasswordRepository oneTimePasswordRepository;

  public LoginResult login(
      String email,
      String phone,
      String username,
      String password,
      String deviceToken,
      String token,
      String code,
      String clientAddress)
      throws Exception {
    OneTimePassword otp = oneTimePasswordRepository.getByToken(token);
    LoginResult loginResult = new LoginResult();
    if (otp == null) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Invalid token.");
      return loginResult;
    }
    if (otp.getExpiry().isBefore(java.time.LocalDateTime.now())) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("OTP validity has expired.");
      return loginResult;
    }
    if (!(code.equals(otp.getCode()))) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Invalid code.");
      return loginResult;
    }
    BaseUser user = otp.getUser();
    if (user == null) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Invalid user.");
      return loginResult;
    }
    recordLoginHistory(user, true, "", clientAddress);
    loginResult.setSuccess(true);
    loginResult.setUserObject(user);
    String type = ((String) user.getClass().getSimpleName());
    String id = String.valueOf(user.getId());
    String finalToken =
        jwtTokenUtil.generateToken(
            id, new UserProxy(type, user.getId(), UUID.randomUUID().toString()));
    loginResult.setToken(finalToken);
    if (deviceToken != null) {
      user.setDeviceToken(deviceToken);
      store.Database.get().save(user);
    }
    return loginResult;
  }

  private void recordLoginHistory(
      BaseUser user, boolean success, String failureReason, String clientAddress) {
    UserLoginRecord loginRecord = new UserLoginRecord();
    loginRecord.setTimeStamp(java.time.LocalDateTime.now());
    loginRecord.setSuccess(success);
    loginRecord.setFailureReason(failureReason);
    loginRecord.setUser(user);
    loginRecord.setIPAddress(clientAddress);
    store.Database.get().save(loginRecord);
  }
}
