package com.cognizant.truyum.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.service.CartService;

@RestController
public class CartController {

	final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
	@Autowired
	private CartService cartservice;
	
	@PostMapping("/addcartitem/{id}/{mid}")
	public ResponseEntity<String> addCartitem(@PathVariable("id") int userid, @PathVariable("mid") int menuitemid) {
		LOGGER.info("START");
		LOGGER.info("In addCartitem in CartController class");
		cartservice.addCartitem(userid, menuitemid);
		LOGGER.info("END");
		return ResponseEntity.ok("Updated Successfully");
	}
	@GetMapping("/allcartitems/{id}")
	public ResponseEntity<Set<MenuItem>> getAllcartitems(@PathVariable("id") int userid) {
		LOGGER.info("START");
		LOGGER.info("In getAllcartitems in CartController class");
		Set<MenuItem> menuitemlist = cartservice.getAllcartitems(userid);
		LOGGER.info("END");
		return ResponseEntity.ok(menuitemlist);
	}
	
	@DeleteMapping("/deletecartitem/{id}/{mid}")
	public ResponseEntity<String> removeCartitem(@PathVariable("id") int userid, @PathVariable("mid") int menuitemid) {
		LOGGER.info("START");
		LOGGER.info("In removeCartitem in CartController class");
		cartservice.removeCartitem(userid, menuitemid);
		LOGGER.info("END");
		return ResponseEntity.ok("Deleted Successfully");
	}
}
