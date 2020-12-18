package com.techelevator.tenmo.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class Accounts {
	
	private long accountId;
	@NotBlank(message = "The field 'userId' should not be empty.")
	private long userId;
	@DecimalMin( value = "1.0", message = "The field 'balance' should be greater than 0.")
	private BigDecimal balance;
	
	public Accounts() {
		
	}

	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
//	@Override
//	public String toString() {
//		
//	}
}
