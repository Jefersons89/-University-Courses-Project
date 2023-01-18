package com.springframework.universitycourses.configuration.security;

import com.springframework.universitycourses.configuration.AuthenticationConfigConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JWTTokenGenerator
{
	public String generateToken(Authentication authentication)
	{
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + AuthenticationConfigConstants.EXPIRATION_TIME);

		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, AuthenticationConfigConstants.SECRET)
				.compact();
	}

	public String getUsernameFromJWT(String token)
	{
		Claims claims = Jwts.parser()
				.setSigningKey(AuthenticationConfigConstants.SECRET)
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}

	public boolean validateToken(String token)
	{
		try
		{
			Jwts.parser().setSigningKey(AuthenticationConfigConstants.SECRET).parseClaimsJws(token);
			return true;
		}
		catch (Exception e)
		{
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
		}
	}
}
