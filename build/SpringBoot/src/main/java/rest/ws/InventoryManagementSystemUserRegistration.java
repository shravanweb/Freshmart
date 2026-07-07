package rest.ws;

import classes.LoginResult;
import classes.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.jpa.OrganizationRepository;
import repository.jpa.UserRepository;
import security.JwtTokenUtil;

@Component("registerInventoryManagementSystemUser")
public class InventoryManagementSystemUserRegistration implements Authenticator {
  @Autowired private UserRepository userRepository;
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Override
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
    LoginResult result =
        classes.RegistrationService.register(
            username,
            email,
            password,
            null,
            userRepository,
            organizationRepository,
            passwordEncoder,
            jwtTokenUtil);

    if (result.isSuccess() && deviceToken != null && result.getUserObject() != null) {
      result.getUserObject().setDeviceToken(deviceToken);
      store.Database.get().save(result.getUserObject());
    }

    return result;
  }
}
