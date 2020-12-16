package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.TransfersDao;
import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;

@RestController
public class TenmoController {

	private AccountsDao accountsDao;
	private TransfersDao transfersDao;
	
	public TenmoController(AccountsDao accountsDao, TransfersDao transfersDao) {
		this.accountsDao = accountsDao;
		this.transfersDao = transfersDao;
	}
	
	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
	public List<Transfers> getTransfers(@PathVariable Long accountFrom, @PathVariable Long accountTo) {
		return transfersDao.getTransfers(accountFrom, accountTo);
	}
	
	@RequestMapping(path = "/accounts", method = RequestMethod.GET)
	public List<Accounts> listAccounts() {
		return accountsDao.listAccounts();
	}
	
	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.GET)
	public Accounts getAccount(@PathVariable int accountId) {
		return accountsDao.getAccount(accountId);
	}
}
