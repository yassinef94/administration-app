package com.project.config;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

	private static final long serialVersionUID = 1L;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.ms}")
	private int jwtExpirationMs;

	public String generateJwtToken(String uuid) {
		return Jwts.builder().setSubject((uuid)).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Error Class JwtTokenUtil Invalid JWT signature :: "+e.toString());
		} catch (MalformedJwtException e) {
			logger.error("Error Class JwtTokenUtil Invalid JWT token :: "+e.toString());
		} catch (ExpiredJwtException e) {
			logger.error("Error Class JwtTokenUtil JWT token is expired :: "+e.toString());
		} catch (UnsupportedJwtException e) {
			logger.error("Error Class JwtTokenUtil JWT token is unsupported :: "+e.toString());
		} catch (IllegalArgumentException e) {
			logger.error("Error Class JwtTokenUtil JWT claims string is empty :: "+e.toString());
		}
		return false;
	}

}
