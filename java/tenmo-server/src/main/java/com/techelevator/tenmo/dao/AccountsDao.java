package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Accounts;

public interface AccountsDao {
	
	Accounts getAccount(int accountId);
	
	List<Accounts> findUser(int userId);
	
	Accounts updateAccount(int userId, BigDecimal balance);
	
}
