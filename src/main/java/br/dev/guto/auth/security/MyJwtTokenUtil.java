package br.dev.guto.auth.security;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.dev.guto.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class MyJwtTokenUtil {
	private static final String SECRET_KEY = "12345678901234567890123456789012";
	private static final long EXPIRATION = 2*60*1000; //TWO MINUTES
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String REQ_HEADER = "Authorization";
	private static final String ISSUER = "*Guto";
	
	public static String createToken(User user) {
		
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		
		String tokenJwt = Jwts.builder()
						  .setSubject(user.getUsername())
						  .setSubject(ISSUER)
						  .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
						  .signWith(key, SignatureAlgorithm.HS256)
						  .compact();
		
		System.out.println("Created token = " + tokenJwt);
		
		return TOKEN_PREFIX + tokenJwt;
	}
	
	private static boolean isIssuerValid(String issuer) {
		return issuer.equals(ISSUER);
	}
	
	private static boolean isSubjectValid(String subject) {
		return subject != null && subject.length() > 0;
	}
	
	private static boolean isExpirationValid(Date expiration) {
		return expiration.after(new Date(System.currentTimeMillis()));
	}
	
	public static Authentication getAuthenticationToken(HttpServletRequest request) throws Exception {
		
		String token = request.getHeader(REQ_HEADER);
		
		token = token.replace(TOKEN_PREFIX, "");
		byte[] secretBytes = SECRET_KEY.getBytes();
		Jws<Claims> jwsClaims = Jwts.parserBuilder()
									.setSigningKey(secretBytes)
									.build()
									.parseClaimsJws(token);
		
		String subject = jwsClaims.getBody().getSubject();
		String issuer = jwsClaims.getBody().getIssuer();
		Date expiration = jwsClaims.getBody().getExpiration();
		
		if(isSubjectValid(subject) && isIssuerValid(issuer) && isExpirationValid(expiration)) {
			return new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
		}
		return null;
	}
	
}
