package com.cognizant.truyum.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.truyum.exceptionhandling.ItemNotFoundException;
import com.cognizant.truyum.exceptionhandling.ListEmptyException;
import com.cognizant.truyum.exceptionhandling.UserNotFound;
import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.model.User;
import com.cognizant.truyum.repository.MenuItemRepository;
import com.cognizant.truyum.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MenuItemRepository menuItemRepository;
	final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);
	@Override
	public void addCartitem(int userid, int menuitemid) {
		/*
		 * LOGGER.info("In addCartitem method in CartServiceImpl class");
		 * 
		 * String status= cartdao.addCartitem(userid, menuitemid); if(status == null) {
		 * LOGGER.
		 * info("Throwing ItemNotFound Exception. Item with the given id do not exists."
		 * ); LOGGER.info("END"); throw new ItemNotFoundException(); } return status;
		 */
		User user = userRepository.findById(userid).orElse(null);
		if (user == null)
			throw new UserNotFound();
		MenuItem menuItem = menuItemRepository.findById(menuitemid).orElse(null);
		if (menuItem == null)
			throw new NoSuchElementException();
		Set<MenuItem> newList = new HashSet<MenuItem>();
		List<MenuItem> list= user.getMenuItemList().stream().collect(Collectors.toList());
		list.add(menuItem);
		newList = list.stream().collect(Collectors.toSet());
		user.setMenuItemList(newList);
		userRepository.save(user);
		
	}

	@Override
	public Set<MenuItem> getAllcartitems(int userid) {
		/*
		 * LOGGER.info("In getAllcartitems method in CartServiceImpl class");
		 * List<MenuItem> cartitems = cartdao.getAllcartitems(userid); if(cartitems ==
		 * null) { LOGGER.
		 * info("Throwing UserNotFound exception in getAllcartitems method in CartServiceImpl class"
		 * ); LOGGER.info("END"); throw new UserNotFound();
		 * 
		 * } if(cartitems.isEmpty()) { LOGGER.
		 * info("Throwing ListNotEmptyException in getAllcartitems method in CartServiceImpl class"
		 * ); LOGGER.info("END"); throw new ListEmptyException(); }
		 * LOGGER.info("Exiting getAllcartitems method in CartServiceImpl class");
		 * return cartitems;
		 */
		if(userRepository.findById(userid).orElse(null) == null)
			throw new UserNotFound();
		return userRepository.getCartItems(userid);
	}

	@Override
	public void removeCartitem(int userid, int menuitemid) {
		/*
		 * LOGGER.info("In removeCartitem method in CartServiceImpl class"); String
		 * status = cartdao.removeCartitem(userid, menuitemid); if (status == null) {
		 * LOGGER.
		 * info("Throwing user not found exception In removeCartitem method in CartServiceImpl class"
		 * ); LOGGER.info("END"); throw new UserNotFound(); } return status;
		 */
		
		User user = userRepository.findById(userid).orElse(null);
		if (user == null)
			throw new UserNotFound();
		MenuItem menuItem = menuItemRepository.findById(menuitemid).orElse(null);
		if (menuItem == null)
			throw new NoSuchElementException();
		Set<MenuItem> menuItemList = user.getMenuItemList();
		menuItemList.remove(menuItem);
		user.setMenuItemList(menuItemList);
		userRepository.save(user);
	}

}
