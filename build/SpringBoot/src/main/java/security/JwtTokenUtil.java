package security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.validation}")
	private long validation;

	private SecretKey key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateToken(String subject, UserProxy userProxy) {
		Claims claims = Jwts.claims().subject(subject).add("id", userProxy.userId).add("type", userProxy.type)
				.add("sessionId", userProxy.sessionId)
				// We don't know why we are setting scopes like this
				.add("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))).build();

		return Jwts.builder().claims(claims).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + validation * 1000)).signWith(key, Jwts.SIG.HS256)
				.compact();
	}

	public UserProxy validateToken(String token) {
		Claims body = null;
		try {
			body = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
		} catch (Exception e) {
			return null;
		}

		if (body.getExpiration().before(new Date())) {
			return null;
		}
		Long id = body.get("id", Long.class);
		String type = body.get("type", String.class);
		String sessionId = body.get("sessionId", String.class);
		return new UserProxy(type, id, sessionId);
	}
}
