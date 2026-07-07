package rest.ws;

import classes.LoginResult;
import java.util.UUID;
import models.BaseUser;
import models.User;
import models.UserLoginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.jpa.UserRepository;
import security.JwtTokenUtil;
import security.UserProxy;

@Component("loginInventoryManagementSystemUserWithEmailAndPassword")
public class InventoryManagementSystemUserEmailAuthenticator implements Authenticator {
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserRepository userRepository;

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
    User user = userRepository.getByEmail(email.toLowerCase());
    LoginResult loginResult = new LoginResult();
    if (user == null) {
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Invalid authentication details.");
      return loginResult;
    }
    if (!(passwordEncoder.matches(password, user.getPassword()))) {
      recordLoginHistory(user, false, "Invalid authentication details.", clientAddress);
      loginResult.setSuccess(false);
      loginResult.setFailureMessage("Invalid authentication details.");
      return loginResult;
    }
    recordLoginHistory(user, true, "", clientAddress);
    loginResult.setSuccess(true);
    loginResult.setUserObject(user);
    String jwtToken =
        jwtTokenUtil.generateToken(
            email.toLowerCase(), new UserProxy("User", user.getId(), UUID.randomUUID().toString()));
    loginResult.setToken(jwtToken);
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
