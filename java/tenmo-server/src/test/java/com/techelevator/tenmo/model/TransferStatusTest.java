package com.techelevator.tenmo.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TransferStatusTest {

	@Test
	public void get_and_set_status_id() {
		TransferStatus transfer = new TransferStatus();
		transfer.setTransferStatusId(1);
		equals(transfer.getTransferStatusId());
	}
	
	@Test
	public void get_and_set_status_desc() {
		TransferStatus transfer = new TransferStatus();
		transfer.setTransferStatusDesc("Approved");
		equals(transfer.getTransferStatusDesc());
	}

}
