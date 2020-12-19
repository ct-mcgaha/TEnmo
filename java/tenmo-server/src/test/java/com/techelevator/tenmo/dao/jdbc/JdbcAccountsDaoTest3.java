package com.techelevator.tenmo.dao.jdbc;

import static org.junit.Assert.*;

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

public class JdbcAccountsDaoTest3 {
	
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
		dao = new JdbcAccountsDao(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void get_account_by_id() {
		Accounts user3 = dao.getAccountByTransferId(9);
		
		assertNotNull(user3);
		assertEquals(user3.getAccountId(), 3);
	}
	
	private Accounts getAccount(long accountId, BigDecimal balance, long userId) {
		Accounts theAccount = new Accounts();
		theAccount.setAccountId();
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