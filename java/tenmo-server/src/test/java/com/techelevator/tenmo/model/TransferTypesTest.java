package com.techelevator.tenmo.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TransferTypesTest {

	@Test
	public void get_and_set_type_id() {
		TransferStatus transfer = new TransferStatus();
		transfer.setTransferStatusId(2);
		equals(transfer.getTransferStatusId());
	}
	
	@Test
	public void get_and_set_type_desc() {
		TransferStatus transfer = new TransferStatus();
		transfer.setTransferStatusDesc("Approved");
		equals(transfer.getTransferStatusId());
	}

}
