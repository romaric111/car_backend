package com.packt.cardatabase.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.packt.cardatabase.domain.AccountCredentials;
import com.packt.cardatabase.service.JwtService;

@RestController
public class LoginController {
	
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
		super();
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials){
		
		System.out.println("LOGIN CONTROLLER HIT");
		
		//it will generate a token and then send it in the response header
		UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
		
		Authentication auth = authenticationManager.authenticate(creds);
		
		// generate token
		String jwts = jwtService.getToken(auth.getName());
		
		//build response with the generated token 
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer"+jwts).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();
		
	}
	

}
