package com.techelevator.tenmo.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.techelevator.tenmo.model.Transfers;



public class JdbcTransfersDaoTest {
	
	private static SingleConnectionDataSource dataSource;
	private JdbcTransfersDao dao;

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
		dao = new JdbcTransfersDao(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void get_transfer_by_id() {
		Transfers theTransfer = new Transfers();
		String sqlGetTransfer = "SELECT * FROM transfers WHERE transfer_id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetTransfer, 1);
		
		if (results.next()) {
			theTransfer = mapRowToTransfers(results);
		}
		
		Transfers newTransfer = dao.getOne(1);
		
		assertEquals(theTransfer.getTransferId(), newTransfer.getTransferId());
	}
//
//	Issues with test below: "no results returned by query"
//  So... issue is that it's not creating a transfer and then returning it
//	1st try: Transfers theTransfer = new Transfer([I filled in all of this])
//	Had issues with out of bounds (6 columns vs. 5 in constructor)
//
//  Another one to ask Margaret about
//	
//	@Test
//	public void send_transfer() {
//		BigDecimal transferAmount = new BigDecimal(6.00);
//		Transfers theTransfer = new Transfers();
//		//sendTransfer(long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount) {
//		String sqlSendTransfer = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES(?,?,?,?,?)";
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSendTransfer, 2, 2, 1, 2, 6.00);
//		
//		if (results.next()) {
//			theTransfer = mapRowToTransfers(results);
//		}
//		
//		Transfers newTransfer = new Transfers((long)66, (long)2, (long)2, (long)1, (long)2, transferAmount);
//		
//		assertEquals(theTransfer.getAccountFrom(), newTransfer.getAccountFrom());
//	}

	
//	Cannot convert Transfers to List<Transfers>...
// 	Not too worried about testing this since it works, but when we review with Margaret,
//  this is a good question to ask
//
//	
//	@Test
//	public void get_transfers_by_user_id() {
//		List<Transfers> userTransfers = new ArrayList<Transfers>();
//		String sqlGetTransfersList = "SELECT * FROM transfers JOIN accounts ON transfers.account_to = accounts.account_id JOIN users ON accounts.user_id = users.user_id WHERE transfers.account_from = ? OR transfers.account_to = ?";
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		List<SqlRowSet> results = jdbcTemplate.queryForRowSet(sqlGetTransfersList, 1, 1);
//		
//		if (results.next()) {
//			userTransfers = mapRowToTransfers(results);
//		}
//		
//		List<Transfers> newTransfers = dao.getTransferForUser(1);
//		assertTrue(userTransfers.equals(newTransfers));
//	}
	
	
	
	private Transfers getTransfer(long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount) {
		Transfers theTransfer = new Transfers();
		theTransfer.setTransferTypeId(transferTypeId);
		theTransfer.setTransferStatusId(transferStatusId);
		theTransfer.setAccountFrom(accountFrom);
		theTransfer.setAccountTo(accountTo);
		theTransfer.setAmount(amount);
		return theTransfer;
	}
	
	private Transfers mapRowToTransfers(SqlRowSet results) {
		Transfers theTransfer = new Transfers();
		theTransfer.setTransferId(results.getLong("transfer_id"));
		theTransfer.setTransferTypeId(results.getLong("transfer_type_id"));
		theTransfer.setTransferStatusId(results.getLong("transfer_status_id"));
		theTransfer.setAccountFrom(results.getLong("account_from"));
		theTransfer.setAccountTo(results.getLong("account_to"));
		theTransfer.setAmount(results.getBigDecimal("amount"));
		return theTransfer;
	}

}
