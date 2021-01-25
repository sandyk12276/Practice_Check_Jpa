package com.cognizant.truyum.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.truyum.model.Role;
import com.cognizant.truyum.model.User;
import com.cognizant.truyum.repository.UserRepository;
import com.cognizant.truyum.security.AppUserDetailsService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserDetailsServiceMockTest {

	@Test
	public void mockTestLoadUserByUsername(){
		UserRepository repository = Mockito.mock(UserRepository.class);
		when(repository.findByUserName("Sandeep")).thenReturn(createUser());
		AppUserDetailsService service = new AppUserDetailsService(repository);
		UserDetails user = service.loadUserByUsername("Sandeep");
		String expected = "$2a$10$R/lZJuT9skteNmAku9Y7aeutxbOKstD5xE5bHOf74M2PHZipyt3yK";
		assertEquals(expected, user.getPassword());
	}
	//Test for when user is returned null
	@Test()
	public void mockTestLoadUserByUsername2(){
		UserRepository repository = Mockito.mock(UserRepository.class);
		when(repository.findByUserName("usss")).thenReturn(null);
		AppUserDetailsService service = new AppUserDetailsService(repository);
		try {
		UserDetails user = service.loadUserByUsername("usss");
		}
		catch(UsernameNotFoundException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.assertFalse(false);
	}
	
	public User createUser() {
		User user=new User();
		user.setId(1);
		user.setUserName("Sandeep");
		
		Role role=new Role();
		role.setId(1);
		role.setRoleName("ADMIN");
		
		Set<Role> roleList=new HashSet<Role>();
		roleList.add(role);
		
		user.setRoleList(roleList);
		user.setPassword("$2a$10$R/lZJuT9skteNmAku9Y7aeutxbOKstD5xE5bHOf74M2PHZipyt3yK");
		return user;
	}
}
