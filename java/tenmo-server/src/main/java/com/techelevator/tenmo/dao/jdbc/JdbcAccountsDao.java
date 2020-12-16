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
	
	private Accounts mapRowToAccounts(SqlRowSet results) {
		Accounts theAccount = new Accounts();
		theAccount.setAccountId(results.getInt("account_id"));
		theAccount.setBalance(results.getBigDecimal("balance"));
		theAccount.setUserId(results.getInt("user_id"));
		
		return theAccount;
	}
}
