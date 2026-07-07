package rest;

public interface OAuthAccount {

	public String createLink(Object state);

	public String getScope();

	public String getClientId();

	public String getAuthorizationUri();

	public String getClientSecret();

	public String getTokenUri();

	public String getSuccessHtml();

	public void onAccessToken(Object context, String accessToken, String refreshToken, int expiresIn);

	public void onAccessTokenFailed(Object context);
}
