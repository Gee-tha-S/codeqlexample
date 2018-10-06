package com.todo.user.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.todo.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*************************************************************************************************************
 *
 * purpose:To generate jwtToken and to decode the token
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
@Component("tokens")
public class JwtTokens { 
	final static String KEY = "sowjanya";

	/**
	 * purpose: to create a token
	 * 
	 * @param user
	 * @return token
	 */
	public String createToken(User user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		String subject = user.getId();
		String issuer = user.getEmailId();
		Date date = new Date();
		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(date).setIssuer(issuer)
				.signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

	/**
	 * purpose: Decoding the token(to get details of user)
	 * 
	 * @param Jwt
	 * @return
	 */
	public Claims parseJwt(String jwt) {
		return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();

	}

}
