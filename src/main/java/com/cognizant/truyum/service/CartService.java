package com.cognizant.truyum.service;

import java.util.List;
import java.util.Set;

import com.cognizant.truyum.model.MenuItem;

public interface CartService {
	public void addCartitem(int userid, int menuitemid);
	 public Set<MenuItem> getAllcartitems(int userid);
	 public void removeCartitem(int userid, int menuitemid);
}
