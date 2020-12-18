package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfers;

public interface TransfersDao {
	
//	List<Transfers> getTransfers(long accountFrom, long accountTo);
	
	List<Transfers> getTransfers();
	
	Transfers getOne(long transferId);
	
	Transfers addTransfer(Transfers transfer);
	
	void sendTransfer(long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount);
	
	List<Transfers> getTransferForUser(long id);

}
