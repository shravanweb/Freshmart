package rest;

import org.springframework.web.util.UriComponentsBuilder;

import classes.ContextHelper;
import classes.Env;

public abstract class AbsOAuthAccount implements OAuthAccount {

	protected abstract String name();

	@Override
	public String createLink(Object state) {
		String url = UriComponentsBuilder.fromUriString(getAuthorizationUri()).queryParam("response_type", "code")
				.queryParam("client_id", getClientId()).queryParam("redirect_uri", redirectUri())
				.queryParam("state", name() + ":" + ContextHelper.createContext(state)).queryParam("scope", getScope())
				.queryParam("access_type", "offline").queryParam("prompt", "consent select_account").toUriString();
		return url;
	}

	private static String redirectUri() {
		return Env.get().getBaseHttpUrl() + "/api/oauth2/callback";
	}
}
