package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
//	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
//	public List<Transfers> getTransfers(@PathVariable Long accountFrom, @PathVariable Long accountTo) {
//		return transfersDao.getTransfers(accountFrom, accountTo);
//	}
	
	@RequestMapping(path = "/accounts", method = RequestMethod.GET)
	public List<Accounts> listAccounts() {
		return accountsDao.listAccounts();
	}
	
	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.GET)
	public Accounts getAccount(@PathVariable int id) {
		return accountsDao.getAccount(id);
	}
	
	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.PUT)
	public Accounts update(@RequestBody Accounts updatedAccount, @PathVariable int id) {
		return accountsDao.updateAccount(updatedAccount, id);
	}
	
	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
	public List<Transfers> getTransfers() {
		return transfersDao.getTransfers();
	}
	
	@RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
	public Transfers getOne(@PathVariable int id) {
		return transfersDao.getOne(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/transfers", method = RequestMethod.POST)
	public Transfers create(@RequestBody Transfers newTransfer) {
		return transfersDao.addTransfer(newTransfer);
	}
<<<<<<< HEAD
=======
	
	@RequestMapping(path = "/transfers", method = RequestMethod.PUT)
	public Accounts sendBucks(@RequestBody Accounts sender, @PathVariable int id) {
		return accountsDao.updateSender(sender, id);
	}
	
//	@RequestMapping (path = "/accounts/{id}", method = RequestMethod.GET)
//	public Accounts getAccountsByTranserID(@PathVariable int id) {
//		return accountsDao.getAccountByTransferId(id);
//	}
>>>>>>> b51a402c8b84882fce7495b72261de9797d88230
}
