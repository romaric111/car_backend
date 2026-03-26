package com.packt.cardatabase;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.packt.cardatabase.service.JwtService;

import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	public AuthenticationFilter(JwtService jwtService) {
		super();
		this.jwtService = jwtService;
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
	    return request.getServletPath().equals("/login");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)throws ServletException, java.io.IOException {
		String jws = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (jws != null && jws.startsWith("Bearer ")) {
			String user = jwtService.getAuthUser(request);

			if (user != null) {
				Authentication authentication =
						new UsernamePasswordAuthenticationToken(user, null, java.util.Collections.emptyList());

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);
	}
}