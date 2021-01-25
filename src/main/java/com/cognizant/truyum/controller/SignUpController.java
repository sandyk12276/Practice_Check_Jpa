package com.cognizant.truyum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.truyum.exceptionhandling.UserExists;
import com.cognizant.truyum.model.Role;
import com.cognizant.truyum.model.User;
import com.cognizant.truyum.repository.RoleRepository;
import com.cognizant.truyum.security.AppUserDetailsService;
import com.cognizant.truyum.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@RestController
@RequestMapping("/user")
//@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class SignUpController {

	final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);
	//private final AppUserDetailsService appUserDetailsService;
	
	@Autowired
	private RoleRepository roleDao;
	//private  AppUserDetailsService  appUserDetailsService;
	private UserService userService;

	@Autowired
    public SignUpController(UserService userService) {
		LOGGER.info("Set user details");
       this.userService =  userService;
       
    }

    @GetMapping("/{username}")
    public boolean userExists(@PathVariable("username") String username ) {
    	LOGGER.info("Checking if user exists");
        return userService.userExists(username);
    }

    @PostMapping("/signup")
    public String add(@RequestBody @Valid User user) {
    	if(!userService.userExists(user.getUserName())) {
    		LOGGER.info("In adduser method");
    		
    		Role role = roleDao.findById(1).get();
            Set<Role> roleList = new HashSet<Role>();
            roleList.add(role);
    		//user.setRoleList(roleList);
    		userService.addUser(user);
        return "added";
        }
    	else
    		throw new UserExists();
    }

	
	
}
