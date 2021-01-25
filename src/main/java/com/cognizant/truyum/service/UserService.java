package com.cognizant.truyum.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.truyum.exceptionhandling.UserExists;
import com.cognizant.truyum.model.User;
import com.cognizant.truyum.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	@Transactional
	public Boolean userExists(String username) {
		User user = userRepository.findByUserName(username);
		if(user == null)
			return false;
		return true;
	}
	
	@Transactional
	public void addUser(User user) {
		User user1 = userRepository.findByUserName(user.getUserName());
		if(user1 == null)
			userRepository.save(user);
		else
			throw new UserExists();
		
	}
}
