package com.techelevator.tenmo.dao.jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.dao.TransfersDao;
import com.techelevator.tenmo.model.Transfers;

@Component
public class JdbcTransfersDao implements TransfersDao{
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTransfersDao(DataSource dataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Transfers> getTransfers() {
		List<Transfers> allTransfers = new ArrayList<>();
		String sqlAllTransfers = "SELECT * FROM transfers";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllTransfers);
		
		while (results.next()) {
			Transfers theTransfers = mapRowToTransfers(results);
			allTransfers.add(theTransfers);
		}
		return allTransfers;
	}
	

	@Override
	public Transfers getOne(long transferId) {
		Transfers oneTransfer = new Transfers();
		String sqlGetOneTransfer = "SELECT * FROM transfers WHERE transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetOneTransfer, transferId);
		
		if (results.next()) {
			oneTransfer = mapRowToTransfers(results);
		}
		return oneTransfer;
	} 
	
	@Override
	public List<Transfers> getTransferForUser(long id) {
		List<Transfers> allTransfersByUser = new ArrayList<>();
		String sqlTransfersForUser = "SELECT * FROM transfers JOIN accounts ON transfers.account_to = accounts.account_id JOIN users ON accounts.user_id = users.user_id WHERE transfers.account_from = ? OR transfers.account_to = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlTransfersForUser, id, id);
		
		while (results.next()) {
			Transfers allTransfers = mapRowToTransfers(results);
			allTransfersByUser.add(allTransfers);
		}
		return allTransfersByUser; 
	} 
	
	@Override
	public Transfers addTransfer(Transfers transfer) {
		String sqlAddTransfer = "INSERT INTO transfers(account_from, account_to, amount, transfer_type_id, transfer_status_id) VALUES (?,?,?,?,?)";
		jdbcTemplate.update(sqlAddTransfer, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), transfer.getTransferTypeId(), transfer.getTransferStatusId());
		return transfer;
	}
	
	
	@Override
	public void sendTransfer(long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount) {
		String sqlSendTransfer = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES(?,?,?,?,?)";
		jdbcTemplate.update(sqlSendTransfer, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
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
