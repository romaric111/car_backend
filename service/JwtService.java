package com.packt.cardatabase.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtService {
	
	static final long EXPIRATIONTIME=86400000;
	//1 days expiration time in milliS, in production we gonna make it shorter
	static final String PREFIX = "Bearer";
	
	//Generate a secret key. This is only for demo purposes
	//In prod, we will read it from the application configuration
	static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	//Now we generate a signed JWT Token
	public String getToken(String username) {
		String token = Jwts.builder()
		.setSubject(username)
		.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
		.signWith(key)
		.compact();
		return token;
	}
	
	//Get a token fromrequest authorisationheader 
	//verify the token and get the username
	
	public String getAuthUser(HttpServletRequest request) {
		String token  = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (token != null) {
			String user = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token.replace(PREFIX, ""))
			.getBody()
			.getSubject();
		if (user != null) 
			return user;
		}
	return null;
	}

}
