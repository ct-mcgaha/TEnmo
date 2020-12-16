package com.techelevator.tenmo.dao.jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.model.Accounts;

@Component
public class JdbcAccountsDao implements AccountsDao {
	
	private JdbcTemplate jdbcTemplate;

	public JdbcAccountsDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private String sqlJoinSender = "SELECT * FROM accounts JOIN transfers ON accounts.account_id = transfers.account_from";
	private String sqlJoinReceiver = "SELECT * FROM accounts JOIN transfers ON accounts.account_id = transfers.account_to";

	@Override
	public List<Accounts> listAccounts() {
		List<Accounts> allAccounts = new ArrayList<>();
		String sqlAllAccounts = "SELECT * FROM accounts";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllAccounts);
		while (results.next()) {
			Accounts theAccounts = mapRowToAccounts(results);
			allAccounts.add(theAccounts);
		}
		
		return allAccounts;
	}

	@Override
	public Accounts getAccount(int accountId) {
		Accounts oneAccount = new Accounts();
		String sqlgetOneAccount = "SELECT * FROM accounts WHERE account_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlgetOneAccount, accountId);
		
		if (results.next()) {
			oneAccount = mapRowToAccounts(results);
		}
		// TODO Auto-generated method stub
		return oneAccount;
	}

	@Override
	public Accounts updateAccount(Accounts account, int accountId) {
		String updateAccount = "UPDATE accounts SET balance = ?, user_id = ? WHERE account_id = ?";
		jdbcTemplate.update(updateAccount, account.getBalance(), account.getUserId(), accountId);
		// TODO Auto-generated method stub
		return account;
	}
	
	/*
	 *  This is a method that updates the SENDER'S account...
	 *  It SUBTRACTS whatever the transfer amount is  the balance.
	 *  I think we need to find a way for it intake "amount" from Transfers.java
	 */
	@Override
	public Accounts updateSender(Accounts account, int accountId) {
		String sendBucks = sqlJoinSender + "UPDATE accounts"+
				"SET balance = (balance - transfers.amount)" + 
				"FROM transfers" + 
				"WHERE transfers.transfer_id = ?";
		jdbcTemplate.update(sendBucks, account.getBalance(), account.getUserId(), accountId);
		return account;
	}
	
	/*
	 * 	This is a method that updates the RECEIVER'S account...
	 *  It ADDS whatever the transfer amount is TO the balance.
	 *  I think we need to find a way for it intake "amount" from Transfers.java
	 */
	@Override
	public Accounts updateReceiver(Accounts account, int accountId) {
		String receiveBucks = sqlJoinReceiver + "UPDATE accounts"+
				"SET balance = (balance - transfers.amount)" + 
				"FROM transfers" + 
				"WHERE transfers.transfer_id = ?";
		jdbcTemplate.update(receiveBucks, account.getBalance(), account.getUserId(), accountId);
		return account;
	}
	
	@Override
	public Accounts getAccountByTransferId(int transferId) {
		Accounts transIdAccount = new Accounts();
		String sql = "SELECT balance FROM accounts\r\n" + 
				"JOIN transfers ON accounts.account_id = transfers.account_to\r\n" + 
				"WHERE transfers.transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
		if (results.next()) {
			transIdAccount = mapRowToAccounts(results);
		}
		return transIdAccount;
	}
	
	private Accounts mapRowToAccounts(SqlRowSet results) {
		Accounts theAccount = new Accounts();
		theAccount.setAccountId(results.getInt("account_id"));
		theAccount.setBalance(results.getBigDecimal("balance"));
		theAccount.setUserId(results.getInt("user_id"));
		
		return theAccount;
	}
}
