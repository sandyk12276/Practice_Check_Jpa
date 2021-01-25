package com.cognizant.truyum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.truyum.model.User;
import com.cognizant.truyum.repository.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	public AppUserDetailsService(UserRepository repository) {
		this.userRepository=repository;
	}
	public AppUserDetailsService() {
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUserName(username);
		
		
		/*
		 * if(username!=null) { AppUser appUser =new
		 * AppUser(userRepository.findByUserName(username) ); return appUser; }
		 * 
		 * return null;
		 */
		if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
		AppUser appUser =new AppUser(user);
        return appUser;
	
	}

}
