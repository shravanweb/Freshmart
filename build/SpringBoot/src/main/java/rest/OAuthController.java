package rest;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import classes.ContextHelper;
import classes.Env;

@RestController
@RequestMapping("/api/oauth2")
public class OAuthController {

	@Autowired
	private Map<String, OAuthAccount> accounts;

	private RestTemplate restTemplate = new RestTemplate();

	private static String redirectUri() {
		return Env.get().getBaseHttpUrl() + "/api/oauth2/callback";
	}

	@GetMapping("/callback")
	public ResponseEntity<?> callback(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state) {
		String name = state.split(":")[0];
		OAuthAccount acc = accounts.get(name);

		// Exchange the authorization code for an access token
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("grant_type", "authorization_code");
		requestBody.put("code", code);
		requestBody.put("redirect_uri", redirectUri());
		requestBody.put("client_id", acc.getClientId());
		requestBody.put("client_secret", acc.getClientSecret());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

		// Send request to token URI
		ResponseEntity<String> response = restTemplate.exchange(acc.getTokenUri(), HttpMethod.POST, requestEntity,
				String.class);

		Object context = ContextHelper.extractContext(state.replaceFirst(name + ":", ""));
		if (response.getStatusCode() == HttpStatus.OK) {
			// Token received successfully
			String responseBody = response.getBody();
			JSONObject obj = new JSONObject(responseBody);
			String accessToken = obj.getString("access_token");
			String refreshToken = obj.getString("refresh_token");
			int expiresIn = obj.getInt("expires_in");
			acc.onAccessToken(context, accessToken, refreshToken, expiresIn);
			return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(acc.getSuccessHtml());
		} else {
			acc.onAccessTokenFailed(context);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error exchanging code for token");
		}
	}
}
