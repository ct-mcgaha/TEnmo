package com.techelevator.tenmo.model;

import static org.junit.Assert.*;


import org.junit.Test;



public class UserTest {

	@Test
	public void get_and_set_username() {
		User user = new User();
		user.setUsername("John");
		
		equals(user.getUsername());
	}
	
	@Test
	public void get_and_set_id() {
		User user = new User();
		user.setId((long)2);
		
		equals(user.getId());
	}
	
	@Test
	public void get_and_set_password() {
		User user = new User();
		user.setPassword("random");
		
		equals(user.getPassword());
	}

	@Test
	public void get_and_set_activation() {
		User user = new User();
		user.setActivated(true);
		
		equals(user.isActivated());
	}
}
