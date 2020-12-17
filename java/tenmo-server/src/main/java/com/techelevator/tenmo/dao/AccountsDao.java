package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Accounts;

public interface AccountsDao {
	
	List<Accounts> listAccounts();
	
	BigDecimal getAccount(long accountId);
	
//	List<Accounts> findUser(int userId);
	
	Accounts updateAccount(Accounts account, long accountId);
	
	Accounts getAccountByTransferId(int transferId);

	void updateSender(BigDecimal transferAmount, long senderId);

	void updateReceiver(BigDecimal amount, long receiverId);
	
	
	
}
