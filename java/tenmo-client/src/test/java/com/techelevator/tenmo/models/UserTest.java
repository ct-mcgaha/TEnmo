package com.techelevator.tenmo.models;

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
		user.setId(1);
		
		equals(user.getId());
	}

}
