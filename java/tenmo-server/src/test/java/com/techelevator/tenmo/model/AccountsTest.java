package com.techelevator.tenmo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class AccountsTest {

	
	@Test
	public void set_everything() {
		Accounts newAccount = new Accounts((long)1, (long)1, BigDecimal.valueOf(100));
		newAccount.setAccountId((long) 1); 
		newAccount.setUserId((long)1);
		newAccount.setBalance(BigDecimal.valueOf(100));
		assertEquals(newAccount.getAccountId(), newAccount.getUserId(), newAccount.getBalance());
	}

	@Test
	public void get_everything() {
		Accounts newAccount = new Accounts((long)1, (long)1, BigDecimal.valueOf(100));
		assertEquals(newAccount.getAccountId(), newAccount.getUserId(), newAccount.getBalance());
	}
	private void assertEquals(long accountId, long userId, BigDecimal balance) {
		// TODO Auto-generated method stub
		
	} 

}
