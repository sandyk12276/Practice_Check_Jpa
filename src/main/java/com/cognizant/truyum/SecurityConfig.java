package com.cognizant.truyum;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.cognizant.truyum.security.AppUserDetailsService;
import com.cognizant.truyum.security.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	AppUserDetailsService appUserDetailsService;
	
	/*
	 * @Bean public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
	 * LOGGER.info("In inMemoryUserDetailsManager method in SecurityConfig class");
	 * LOGGER.info("Start");
	 * 
	 * List<UserDetails> userDetailsList = new ArrayList<>();
	 * 
	 * userDetailsList.add(User.withUsername("user") .password(passwordEncoder()
	 * .encode("pwd")) .roles("USER").build());
	 * 
	 * userDetailsList.add(User.withUsername("admin") .password(passwordEncoder()
	 * .encode("pwd")) .roles("ADMIN").build());
	 * 
	 * LOGGER.info("End");
	 * 
	 * return new InMemoryUserDetailsManager(userDetailsList);
	 * 
	 * }
	 */
	//@Bean
    //public UserDetailsService userDetailsService() {
    //    return new AppUserDetailsService();
 //   }
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		LOGGER.info("In configure(AuthenticationManagerBuilder auth) method in SecurityConfig class");
		auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder());
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		LOGGER.info("In passwordEncoder method in  SecurityConfig class");
		return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();

		http.csrf().disable().httpBasic().and()
         .authorizeRequests()
         //.antMatchers("/menu-items").permitAll()
         //.antMatchers("/signup").permitAll()
         .antMatchers("/users/adduser").permitAll()
		 .antMatchers("/authenticate").hasAnyAuthority( "USER", "ADMIN" )
         .anyRequest().authenticated()
         .and()
         .addFilter(new JwtAuthorizationFilter(authenticationManager()));
	}
	
	
}
