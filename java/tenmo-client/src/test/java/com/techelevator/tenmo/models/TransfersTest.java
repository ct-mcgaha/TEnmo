package com.techelevator.tenmo.models;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;




public class TransfersTest {
	@Test
	public void set_everything() {
		Transfers newTransfer = new Transfers((long)2, (long)3, (long)2, (long)2, (long)1, BigDecimal.valueOf(1000));
		newTransfer.setTransferId((long) 1);
		newTransfer.setTransferTypeId((long) 3);
		newTransfer.setTransferStatusId((long) 3);
		newTransfer.setAccountFrom((long) 2);
		newTransfer.setAccountTo((long) 1);
		newTransfer.setAmount(BigDecimal.valueOf(100));
		assertEquals(newTransfer.getTransferId(), newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId(), newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
	}

	@Test
	public void get_everything() {		
		Transfers newTransfer = new Transfers((long)2, (long)3, (long)2, (long)2, (long)1, BigDecimal.valueOf(1000));
		assertEquals(newTransfer.getTransferId(), newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId(), newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
	}

	private void assertEquals(Long transferId, Long transferTypeId, Long transferStatusId, Long accountFrom,
			Long accountTo, BigDecimal amount) {
		// TODO Auto-generated method stub
		
	}
	

}
