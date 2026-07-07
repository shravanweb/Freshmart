package rest;

import org.springframework.stereotype.Service;

@Service("DummyOAuth")
public class DummyOAuthAccount extends AbsOAuthAccount {

	@Override
	public String getScope() {
		return null;
	}

	@Override
	public String getClientId() {
		return null;
	}

	@Override
	public String getAuthorizationUri() {
		return null;
	}

	@Override
	public String getClientSecret() {
		return null;
	}

	@Override
	public String getTokenUri() {
		return null;
	}

	@Override
	public String getSuccessHtml() {
		return null;
	}

	@Override
	public void onAccessToken(Object context, String accessToken, String refreshToken, int expiresIn) {
	}

	@Override
	public void onAccessTokenFailed(Object context) {
	}

	@Override
	protected String name() {
		return "DummyOAuth";
	}

}
