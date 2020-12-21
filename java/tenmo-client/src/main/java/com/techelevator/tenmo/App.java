package com.techelevator.tenmo;

import java.math.BigDecimal;
import java.util.Scanner;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfers;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountsService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransfersService;
import com.techelevator.tenmo.services.UserService;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_VIEW_SINGLE_TRANSFER = "View a single transfer";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_VIEW_SINGLE_TRANSFER, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String SEND_MENU_OPTION_CHOOSE_RECEIVER = "Enter the ID of the User you'd like to send (Press 0 to exit)";
	private static final String SEND_MENU_OPTION_CHOOSE_TRANSFER_ID = "Enter the ID of the transfer you'd like to see (Press 0 to exit)";
	private static final String SEND_MENU_OPTION_CHOOSE_AMOUNT = "Enter amount";

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private AccountsService accountsService;
	private TransfersService transfersService;
	private UserService userService;
	private Scanner in;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
		in = new Scanner(System.in);
		accountsService = new AccountsService(API_BASE_URL);
		transfersService = new TransfersService(API_BASE_URL);
		userService = new UserService(API_BASE_URL);
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_SINGLE_TRANSFER.equals(choice)) {
				viewSingleTransfer();
			}
			else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}


	private void viewCurrentBalance() {
		BigDecimal userBalance = accountsService.getAccountBalance(currentUser.getUser().getId(), currentUser.getToken());
		System.out.println("Your current account balance is : $" + userBalance);
	}

	@SuppressWarnings("unlikely-arg-type")
	private void viewTransferHistory() {
		Transfers[] transferHistory = transfersService.viewTransferHistory(currentUser.getUser().getId(), currentUser.getToken());
		System.out.println("Id     From/To                Amount");
		System.out.println("=====================================");
		for (int i = 0; i < transferHistory.length; i++) {
			System.out.printf("%-5d %-15s %-7s %-1s\n", transferHistory[i].getTransferId(),
					"From: " + userService.getUserById(transferHistory[i].getAccountFrom(), currentUser.getToken()).getUsername(), "$", transferHistory[i].getAmount());
			System.out.printf("%-5d %-15s %-7s %-1s\n", transferHistory[i].getTransferId(),
					"To: " + userService.getUserById(transferHistory[i].getAccountTo(), currentUser.getToken()).getUsername(), "$", transferHistory[i].getAmount());
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	private void viewSingleTransfer() {
		Transfers[] transferHistory = transfersService.viewTransferHistory(currentUser.getUser().getId(), currentUser.getToken());
		for (int i = 0; i < transferHistory.length; i++) {
			System.out.println(transferHistory[i].getTransferId());
		}
		boolean choice = false;
		Long nameId = null;
		while (choice == false) {
			nameId = (Long) console.getUserInputLong(SEND_MENU_OPTION_CHOOSE_TRANSFER_ID);
			if (nameId == 0) {
				choice = true;
				return;
			}
			else {
				for (int i = 0; i < transferHistory.length; i++) {
					if (transferHistory[i].getTransferId().equals(nameId)) {
						choice = true;
						String transferTypeId = "Send";
						String transferStatusId = "Approved";
						System.out.printf("%-5d %-15s %-7s %-1s %-10s %-1s %-1s\n", transferHistory[i].getTransferId(),
								"From: " + userService.getUserById(transferHistory[i].getAccountFrom(), currentUser.getToken()).getUsername(), "To: " + userService.getUserById(transferHistory[i].getAccountTo(), currentUser.getToken()).getUsername(),
								"  Type: " + transferTypeId, "  Status: " + transferStatusId, "  $", transferHistory[i].getAmount());
					}
				}
				if (choice == false) {
					System.out.println("Not a valid choice. Choose again");
				}
			}
		}
	}

	private void viewPendingRequests() {
	}

	private void sendBucks() {
		User[] users = userService.getUsers(currentUser.getUser().getId(), currentUser.getToken());
		for (int i = 0; i < users.length; i++) {
			System.out.printf("%5d %-20s\n", users[i].getId(), users[i].getUsername());
		}
		boolean choice = false;
		Integer nameId = null;
		while (choice == false) {
			nameId = (Integer) console.getUserInputInteger(SEND_MENU_OPTION_CHOOSE_RECEIVER);
			if (nameId == 0) {
				choice = true;
				return;
			} else if (nameId == currentUser.getUser().getId()) {
				System.out.println("Can't transfer to same account.");
			}
			else {
				for (int i = 0; i < users.length; i++) {
					if (users[i].getId().equals(nameId)) {
						choice = true;
					}
				}
				if (choice == false) {
					System.out.println("Not a valid choice. Choose again");
				}
			}
		}
		boolean sendAmount = false;
		while (sendAmount == false) {
			BigDecimal amount = console.getUserBigDecimal(SEND_MENU_OPTION_CHOOSE_AMOUNT);
			if (amount.compareTo(BigDecimal.ZERO) > 0) {
				long transferTypeId = 2;
				long transferStatusId = 2;
				transfersService.sendTransfer(transferTypeId, transferStatusId, currentUser.getUser().getId(), nameId,
						amount, currentUser.getToken());
				BigDecimal userBalance = accountsService.getAccountBalance(currentUser.getUser().getId(), currentUser.getToken());
				if (amount.compareTo(userBalance) == 1) {
					System.out.println("Not enough funds");
					System.out.println("Your Current Balance: $" + userBalance);
					return;
				}
				System.out.println("Approved!");
				System.out.println("Your Current Balance: $" + userBalance);
				sendAmount = true;
				return;
			} 
			if (amount.compareTo(BigDecimal.ZERO) == 0) {
				System.out.println("Enter value above 0");
				return;
			}
			else {
				System.out.println("Not able to send negative dollars");
				return;
			}

		}
	}

	private void requestBucks() {
	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) // will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) // will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
