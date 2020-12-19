package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Accounts {
	
	private long accountId;
	private long userId;
	private BigDecimal balance;
	
	public Accounts() {
		
	}
	
	public Accounts(long accountId, long userId, BigDecimal balance) {
		super();
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
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

}
