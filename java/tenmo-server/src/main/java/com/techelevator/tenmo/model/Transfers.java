package com.techelevator.tenmo.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

public class Transfers {
	
	private Long transferId;
	@Min(value = 1, message = "The field 'transferTypeId' should not be blank")
	private Long transferTypeId;
	@Min(value = 1, message = "The field 'transferStatusId' should not be blank")
	private Long transferStatusId;
	@Min(value = 1, message = "The field 'accountFrom' should not be blank")
	private Long accountFrom;
	@Min(value = 1, message = "The field 'accountTo' should not be blank")
	private Long accountTo;
	@DecimalMin(value = "0.0", message = "The field 'amount' should not be blank")
	private BigDecimal amount;
	
	
	
	public Transfers(Long transferId, Long transferTypeId, Long transferStatusId, Long accountFrom, Long accountTo, BigDecimal amount) {
		this.transferId = transferId;
		this.transferTypeId = transferTypeId;
		this.transferStatusId = transferStatusId;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}
	
	
	public Transfers() {
		// TODO Auto-generated constructor stub
	}


	public Long getTransferId() {
		return transferId;
	}
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
	public Long getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(Long transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	public Long getTransferStatusId() {
		return transferStatusId;
	}
	public void setTransferStatusId(Long transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	public Long getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(Long accountFrom) {
		this.accountFrom = accountFrom;
	}
	public Long getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(Long accountTo) {
		this.accountTo = accountTo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	
}
