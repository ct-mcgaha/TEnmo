package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
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
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

@RestController
public class TenmoController {

	private AccountsDao accountsDao;
	private TransfersDao transfersDao;
	private UserDAO userDao;

	public TenmoController(AccountsDao accountsDao, TransfersDao transfersDao, UserDAO userDao) {
		this.accountsDao = accountsDao;
		this.transfersDao = transfersDao;
		this.userDao = userDao;
	}

//	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
//	public List<Transfers> getTransfers(@PathVariable Long accountFrom, @PathVariable Long accountTo) {
//		return transfersDao.getTransfers(accountFrom, accountTo);
//	}

	@RequestMapping(path = "/accounts", method = RequestMethod.GET)
	public List<Accounts> listAccounts() {
		return accountsDao.listAccounts();
	}

	@RequestMapping(path = "/accounts/{id}/balance", method = RequestMethod.GET)
	public BigDecimal getAccount(@PathVariable int id) {
		return accountsDao.getAccountBalance(id);
	}

	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.PUT)
	public Accounts update(@RequestBody Accounts updatedAccount, @PathVariable int id) {
		return accountsDao.updateAccount(updatedAccount, id);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(path = "/accounts/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {
		if (accountsDao.getAccountBalance(id) != null) {
		accountsDao.deleteAccount(id);
		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/transfers/send", method = RequestMethod.POST)
	public void sendTransfer(@RequestBody Transfers sendTransfer) {
		if (accountsDao.getAccountBalance(sendTransfer.getAccountFrom()).compareTo(sendTransfer.getAmount()) > 0) {
			transfersDao.sendTransfer(sendTransfer.getTransferTypeId(), sendTransfer.getTransferStatusId(), sendTransfer.getAccountFrom(), sendTransfer.getAccountTo(), sendTransfer.getAmount());
			accountsDao.updateSender(sendTransfer.getAmount(), sendTransfer.getAccountFrom());
			accountsDao.updateReceiver(sendTransfer.getAmount(), sendTransfer.getAccountTo());
		}
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

	@RequestMapping(path = "/accounts/{id}/transfers", method = RequestMethod.GET)
	public List<Transfers> getTransferById(@PathVariable int id) {
		return transfersDao.getTransferForUser(id);
	}
	
	@RequestMapping (path = "/users", method = RequestMethod.GET)
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	

//	@RequestMapping(path = "/transfers", method = RequestMethod.PUT)
//	public Accounts sendBucks(@RequestBody Accounts sender, @PathVariable int id) {
//		return accountsDao.updateSender(sender, id);
//	}

//	@RequestMapping (path = "/accounts/{id}", method = RequestMethod.GET)
//	public Accounts getAccountsByTranserID(@PathVariable int id) {
//		return accountsDao.getAccountByTransferId(id);
//	}
	
//	@RequestMapping (path = "/users", method = RequestMethod.GET)
//	public List<User> getListOfUsers() {
//		List<User> allUsers = userDao.findAll();
//		return allUsers;
//	}
//	
//	@RequestMapping (path = "/users/{id}", method = RequestMethod.GET)
//	public User getOne(@PathVariable long id) {
//		User aUser = userDao.findByUserId(id);
//		return aUser;
//	}

}
