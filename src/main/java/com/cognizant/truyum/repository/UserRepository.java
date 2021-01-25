package com.cognizant.truyum.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.cognizant.truyum.model.User;
import com.cognizant.truyum.model.MenuItem;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	 User findByUserName(String userName);
	 
	 @Query("SELECT u.menuItemList FROM User u WHERE u.id=:userid")
		public Set<MenuItem> getCartItems(Integer userid);
	
}
