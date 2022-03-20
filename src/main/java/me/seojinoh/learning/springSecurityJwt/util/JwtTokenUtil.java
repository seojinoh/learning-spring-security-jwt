package me.seojinoh.learning.springSecurityJwt.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import me.seojinoh.learning.springSecurityJwt.config.JwtConfig;

@Component
public class JwtTokenUtil {

	public String getToken(String token) {
		if(token.startsWith(JwtConfig.getGRANT_TYPE()) && token.split("\\s").length == 2) {
			return token.split("\\s")[1].trim();
		} else {
			throw new IllegalArgumentException("Failed to get token");
		}
	}

	public String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if(StringUtils.hasText(token) && token.startsWith(JwtConfig.getGRANT_TYPE()) && token.split("\\s").length == 2) {
			return token.split("\\s")[1].trim();
		} else {
			return null;
		}
	}

	public Claims parseToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey(JwtConfig.getSECRET_KEY()))
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public String getUsername(String token) {
		return parseToken(token).get("username", String.class);
	}

	private Key getSigningKey(String secretKey) {
		byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

		return Keys.hmacShaKeyFor(secretKeyBytes);
	}

	public Boolean isExpiredToken(String token) {
		Date expiration = parseToken(token).getExpiration();

		return expiration.before(new Date());
	}

	public String generateAccessToken(String username) {
		return generateToken(username, JwtConfig.getACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS());
	}

	public String generateRefreshToken(String username) {
		return generateToken(username, JwtConfig.getREFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS());
	}

	private String generateToken(String username, long expirationTime) {
		Claims claims = Jwts.claims();
		claims.put("username", username);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(getSigningKey(JwtConfig.getSECRET_KEY()), SignatureAlgorithm.HS256)
				.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsername(token);

		return username.equals(userDetails.getUsername()) && !isExpiredToken(token);
	}

	public long getRemainingTimeInMilliseconds(String token) {
		Date expiration = parseToken(token).getExpiration();
		Date now = new Date();

		return expiration.getTime() - now.getTime();
	}

}
