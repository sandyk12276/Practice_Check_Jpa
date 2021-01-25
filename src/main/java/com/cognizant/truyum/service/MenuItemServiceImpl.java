package com.cognizant.truyum.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.truyum.exceptionhandling.ItemNotFoundException;
import com.cognizant.truyum.exceptionhandling.ListEmptyException;
import com.cognizant.truyum.exceptionhandling.UpdateFailedException;
import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.repository.MenuItemRepository;

@Service
public class MenuItemServiceImpl implements MenuItemService {
	
	@Autowired
   	MenuItemRepository menuItemRepository;
	
	final Logger LOGGER = LoggerFactory.getLogger(MenuItemServiceImpl.class);
	@Override
	public List<MenuItem> getMenuItemListAdmin() {
		LOGGER.info("In getMenuItemListAdmin method in MenuItemServiceImpl class");
		//List<MenuItem> menuitemlist = menuitemdao.getMenuItemListAdmin();
		List<MenuItem> menuitemlist =menuItemRepository.findAll();
		
		if(menuitemlist.isEmpty()) {
			LOGGER.info("Throwing ListEmpty Exception in In getMenuItemListAdmin method in MenuItemServiceImpl class");
			LOGGER.info("END");
			throw new ListEmptyException();}
		return menuitemlist;
	}

	@Override
	public List<MenuItem> getMenuItemListCustomer() {
		LOGGER.info("In getMenuItemListCustomer method in MenuItemServiceImpl class");
		//List<MenuItem> menuitemlist = menuitemdao.getMenuItemListCustomer();
		List<MenuItem> menuitemlist = menuItemRepository.getMenuItemListCustomer();
		if(menuitemlist.isEmpty()) {
			LOGGER.info("Throwing ListEmpty Exception in In getMenuItemListCustomer method in MenuItemServiceImpl class");
			LOGGER.info("END");
			throw new ListEmptyException();
			}
		return menuitemlist;

	}

	@Override
	public String modifyMenuItem(MenuItem menuitem) {
		LOGGER.info("In modifyMenuItem method in MenuItemServiceImpl class");
		
		Optional<MenuItem> result = menuItemRepository.findById(menuitem.getId());
		if (!result.isPresent())
		{	
			throw new ItemNotFoundException();
		}
		try { 
		menuItemRepository.save(menuitem);
		return "Updated";
	}
	catch(UpdateFailedException e) {
		return "Update Failed";
	}
	}

	@Override
	public MenuItem getMenuItem(int menuItemId) {
		Optional<MenuItem> result = menuItemRepository.findById(menuItemId);
		if (!result.isPresent())
		{	
			throw new ItemNotFoundException();
		}
		MenuItem menuItem = result.get();
		return menuItem;
	}

}
