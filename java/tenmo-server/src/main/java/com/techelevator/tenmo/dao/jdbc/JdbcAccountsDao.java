package com.techelevator.tenmo.dao.jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.model.Accounts;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Accounts> findUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Accounts updateAccount(int userId, BigDecimal balance) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Accounts mapRowToAccounts(SqlRowSet results) {
		Accounts theAccount = new Accounts();
		theAccount.setAccountId(results.getInt("account_id"));
		theAccount.setBalance(results.getBigDecimal("balance"));
		theAccount.setUserId(results.getInt("user_id"));
		
		return theAccount;
	}
}
