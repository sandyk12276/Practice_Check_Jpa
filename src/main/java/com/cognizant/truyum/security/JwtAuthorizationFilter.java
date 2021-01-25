package com.cognizant.truyum.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
private String SECRET_KEY="It is a secret";
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		LOGGER.info("In JwtAuthorizationFilter constructor in that class");
		LOGGER.info("Start");
		LOGGER.debug("{}: ", authenticationManager);
	}

	@Override

	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		LOGGER.info("In doFilterInternal in that class");
		LOGGER.info("START");
		//String header = req.getHeader("Authorization");
		String header = req.getHeader("Authorization");
		LOGGER.info(header);
		if (header == null || !header.startsWith("Bearer ")) 
		{
			LOGGER.info("Returning Null");
			LOGGER.info("Exiting doFilterInternal in that class");
			chain.doFilter(req, res);
			return;
		}

		LOGGER.info("Entering getAuthentication method");
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
		LOGGER.info("End");
		LOGGER.info("Exiting doFilterInternal in that class");
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization").split(" ")[1];
		LOGGER.info("Token is: " +token );
		if (token != null) {
			LOGGER.info("Token is not null. Hence entering the if condition");
			//Jws<Claims> jws;
			try {
				LOGGER.info("Entering try block to get user name");
				String user =  Jwts.parser()
						.setSigningKey(SECRET_KEY)
						.parseClaimsJws(token).getBody().getSubject();
				LOGGER.info("User is: " + user);
				if (user != null) {
					LOGGER.info("User not null. Returning user object");
					return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
				}
				} 
			catch (JwtException ex) {
				ex.printStackTrace();
				LOGGER.info("Got an exception. Catching the exception");
				return null;
			}
				
		}
		LOGGER.info("Token is null. Returning null");
	return null;
	}
}
