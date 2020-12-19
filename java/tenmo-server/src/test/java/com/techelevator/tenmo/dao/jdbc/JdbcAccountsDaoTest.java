package com.techelevator.tenmo.dao.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;



public class JdbcAccountsDaoTest {
	
	private static SingleConnectionDataSource dataSource;
	private JdbcAccountsDao dao;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:8080/accounts");
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
	public void get_accounts_by_id() {
		
	}
	
	private Accounts getAccount(long accountId, BigDecimal balance, long userId) {
		Accounts theAccount = new Accounts();
		theAccount.setAccountId(accountId);
		theAccount.setBalance(balance);
		theAccount.setUserId(userId);
		return theAccount;
	}

}
