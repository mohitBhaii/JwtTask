package com.jwt.task.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "intelliatech";
	
	private static final int TOKEN_VALADITY = 3600 * 5;
	
	public String getUsernameFromToken(String token) {

		return getClaimFromToken(token, Claims::getSubject);
	}
	
	private <T> T getClaimFromToken(String token, Function<Claims,T> claimresolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimresolver.apply(claims);
	}
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public boolean validateToken(String token, UserDetails details) {
		
		String usernameFromToken = getUsernameFromToken(token);
		
		return (usernameFromToken.equals(details.getUsername()) && !isTokenExpired(token));
		
	}
	public boolean isTokenExpired(String token) {
		
		Date expirationDateFromToken = getExpirationDateFromToken(token);
		
		return expirationDateFromToken.before(new Date());
		
	}
	
	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		return Jwts.builder().setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ TOKEN_VALADITY * 1000))
				.signWith(SignatureAlgorithm.HS512,SECRET_KEY)
				.compact();
	}
}
