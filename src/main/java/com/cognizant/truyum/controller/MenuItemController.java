package com.cognizant.truyum.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.security.AppUserDetailsService;
import com.cognizant.truyum.service.MenuItemService;

@RestController
public class MenuItemController {

	@Autowired
	private MenuItemService menuitemservice;
	@Autowired
	public AppUserDetailsService appUserDetailsService;
	
	final Logger LOGGER = LoggerFactory.getLogger(MenuItemController.class);
	
	@GetMapping("/menu-item")
	public ResponseEntity<List<MenuItem>> getAllmenuItems(){
		LOGGER.info("In getallmenuitems method in menuitemcontroller class");
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		
        String user = authentication.getName();
        LOGGER.info("User name is: " + user);
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(user);
        String role = userDetails.getAuthorities().toArray()[0].toString();
        LOGGER.info("Role of the user is: " + role);
        if(role.equalsIgnoreCase("ADMIN"))
        	return ResponseEntity.ok(menuitemservice.getMenuItemListAdmin());
        else
        	 return ResponseEntity.ok(menuitemservice.getMenuItemListCustomer());
	}
	
	/*
	 * @GetMapping("/listadmin") public List<MenuItem> getMenuItemListAdmin() {
	 * LOGGER.info("START"); List<MenuItem> menuitemlist =
	 * menuitemservice.getMenuItemListAdmin(); LOGGER.info("END"); return
	 * menuitemlist; }
	 * 
	 * @GetMapping("/listcustomer") public List<MenuItem> getMenuItemListCustomer(){
	 * LOGGER.info("START"); List<MenuItem> menuitemlist =
	 * menuitemservice.getMenuItemListCustomer(); LOGGER.info("END"); return
	 * menuitemlist; }
	 */
	
	@PutMapping("/modifymenu")
	public ResponseEntity<String> modifyMenuItem(@RequestBody MenuItem menuitem) {
		LOGGER.info("START");
		String status = menuitemservice.modifyMenuItem(menuitem);
		LOGGER.info("END");
		return  ResponseEntity.ok("Updated Successfully");
	}
	
	@GetMapping("/getitem/{id}")
	public ResponseEntity<MenuItem> getMenuItem(@PathVariable("id") int menuItemId) {
		LOGGER.info("START");
		MenuItem menuitem = menuitemservice.getMenuItem(menuItemId);
		LOGGER.info("END");
		return ResponseEntity.ok(menuitem);

	}
}
