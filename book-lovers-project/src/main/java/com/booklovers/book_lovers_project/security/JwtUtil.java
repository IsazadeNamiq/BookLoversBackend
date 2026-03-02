package com.booklovers.book_lovers_project.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final Key key;
	private final long jwtExpirationMs;

	public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long jwtExpirationMs) {
		// secret-in uzunluğu uyğun olmalıdır (HS256 üçün ən az ~32 byte)
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.jwtExpirationMs = jwtExpirationMs;
	}

	public String generateToken(String username) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + jwtExpirationMs);
		return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(exp)
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}
}