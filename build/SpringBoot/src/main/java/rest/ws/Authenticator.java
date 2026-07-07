package rest.ws;

import classes.LoginResult;

public interface Authenticator {

	public LoginResult login(String email, String phone, String username, String password, String deviceToken,
			String token, String code, String clientAddress) throws Exception;
}
