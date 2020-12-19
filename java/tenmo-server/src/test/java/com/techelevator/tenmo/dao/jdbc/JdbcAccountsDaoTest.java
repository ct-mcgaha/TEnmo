package com.techelevator.tenmo.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;


import com.techelevator.tenmo.model.Accounts;



public class JdbcAccountsDaoTest {
	
	private static SingleConnectionDataSource dataSource;
	private JdbcAccountsDao dao;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
//		String sqlCreateTestAccount = "INSERT INTO accounts (account_id, balance, user_id) VALUES (?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		jdbcTemplate.update(sqlCreateTestAccount, 4, 999, 1);
		dao = new JdbcAccountsDao(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void get_account_balance_by_id() {
		Accounts theAccount = new Accounts();
		String sqlgetOneAccount = "SELECT * FROM accounts WHERE account_id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlgetOneAccount, 2);
		
		if (results.next()) {
			theAccount = mapRowToAccounts(results);
		}
		BigDecimal account = dao.getAccountBalance(2);
		assertTrue(theAccount.getBalance().equals(account));

		
		
//		Accounts user3 = dao.getAccountByTransferId(9);
//		
//		assertNotNull(user3);
//		assertEquals(user3.getAccountId(), 3);
	}
	
	@Test
	public void create_account() {
		
		BigDecimal originalBalance = new BigDecimal(100);
		BigDecimal newBalance = new BigDecimal(1000);
		
		Accounts theAccount = new Accounts((long)1, (long)1, originalBalance);
		theAccount.setBalance(originalBalance);
		Accounts savedBalance = dao.createAccount(theAccount);
		
		Long accountId = savedBalance.getAccountId();
		dao.getAccount(accountId);
		Accounts updatedAccount = dao.getAccount(accountId);
		
		assertNotNull(updatedAccount);
		assertEquals(originalBalance, originalBalance);
	}
	
	@Test
	public void get_account__by_transfer_id() {
		Accounts theAccount = new Accounts();
		String sql = "SELECT account_from, account_to FROM transfers WHERE transfer_id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, 3);
		if (results.next()) {
			theAccount = mapRowToAccounts(results);
		}
		Accounts newAccount = dao.getAccountByTransferId(3);
		
		assertEquals(theAccount.getAccountId(), newAccount.getAccountId());
	}
	
	private Accounts getAccount(long accountId, BigDecimal balance, long userId) {
		Accounts theAccount = new Accounts();
		theAccount.setAccountId(accountId);
		theAccount.setBalance(balance);
		theAccount.setUserId(userId);
		return theAccount;
	}
	
	private Accounts mapRowToAccounts(SqlRowSet results) {
		Accounts theAccount = new Accounts();
		theAccount.setAccountId(results.getInt("account_id"));
		theAccount.setBalance(results.getBigDecimal("balance"));
		theAccount.setUserId(results.getInt("user_id"));
		
		return theAccount;
	}

}
