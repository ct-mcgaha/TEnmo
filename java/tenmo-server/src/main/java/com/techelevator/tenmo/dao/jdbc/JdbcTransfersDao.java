package com.techelevator.tenmo.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.dao.TransfersDao;
import com.techelevator.tenmo.model.Transfers;

@Component
public class JdbcTransfersDao implements TransfersDao{
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTransfersDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return oneTransfer;
	} 
	
	@Override
	public Transfers addTransfer(Transfers transfer) {
		String sqlAddTransfer = "INSERT INTO transfers(account_from, account_to, amount, transfer_type_id, transfer_status_id) VALUES (?,?,?,?,?)";
		jdbcTemplate.update(sqlAddTransfer, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), transfer.getTransferTypeId(), transfer.getTransferStatusId());
		// TODO Auto-generated method stub
		return transfer;
	}
	
	private Transfers mapRowToTransfers(SqlRowSet results) {
		Transfers theTransfer = new Transfers();
		theTransfer.setAccountFrom(results.getLong("account_from"));
		theTransfer.setAccountTo(results.getLong("account_to"));
		theTransfer.setAmount(results.getBigDecimal("amount"));
		theTransfer.setTransferId(results.getLong("transfer_id"));
		theTransfer.setTransferTypeId(results.getLong("transfer_type_id"));
		theTransfer.setTransferStatusId(results.getLong("transfer_status_id"));
		return theTransfer;
	}

	

}
