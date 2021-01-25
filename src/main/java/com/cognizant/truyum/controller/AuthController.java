package com.cognizant.truyum.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@RestController
public class AuthController {

	final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	private String SECRET_KEY="It is a secret";
	@GetMapping("/authenticate")
	public Map<String,String> authenticate(@RequestHeader("Authorization") String authHeader) {
		LOGGER.info("START");
		LOGGER.info("In authenticate method of  AuthController class");
		LOGGER.debug(authHeader);
		Map<String, String> map=new HashMap<String,String>();
		map.put("token",generateJwt(getUser(authHeader)));
		LOGGER.info("END");
		LOGGER.info("Returning Map");
		return map;
	}
	
	private String getUser(String authHeader) {
		LOGGER.debug(authHeader.substring(6));
		byte[] decoded=  Base64.getDecoder().decode(authHeader.substring(6));
		String str=new String(decoded);
		String[] splitted=str.split(":");
		String user=splitted[0];
		return user;
	}
	
	private String generateJwt(String user) {
		
		JwtBuilder builder = Jwts.builder();
		builder.setSubject(user);
		builder.setIssuedAt(new Date());
		builder.setExpiration(new Date((new Date()).getTime() + 1200000));
		builder.signWith(SignatureAlgorithm.HS256,SECRET_KEY);
		String token = builder.compact();
		return token;
	}
}
