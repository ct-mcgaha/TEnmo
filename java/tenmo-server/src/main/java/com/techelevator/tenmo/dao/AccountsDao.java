package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Accounts;

public interface AccountsDao {
	
	List<Accounts> listAccounts();
	
	BigDecimal getAccountBalance(long accountId);
	
//	List<Accounts> findUser(int userId);
	
//	Accounts updateAccount(Accounts account, long accountId);
	
	Accounts getAccountByTransferId(long transferId);

	void updateSender(BigDecimal transferAmount, long senderId);

	void updateReceiver(BigDecimal transferAmount, long receiverId);
	
	void deleteAccount(long accountId);

	Accounts createAccount(Accounts newAccount);

	Accounts getAccount(long accountId);
	
}
