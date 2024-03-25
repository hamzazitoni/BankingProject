package com.cfgbank.controller;

import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;

import com.cfgbank.model.Account;
import com.cfgbank.model.InsufficientFundsException;
import com.cfgbank.model.KYCException;
import com.cfgbank.service.CustomerService;

public class CustomerController {
	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void handleWithdraw(Account account, double amount) {
		try {
			customerService.withdraw(account, amount);
			System.out.println("Withdrawal successful. New balance: " + account.getBalance());
		} catch (InsufficientFundsException e) {
			System.out.println("Insufficient funds. Withdrawal failed.");
		}
	}

	public void handleDeposit(Account account, double amount) {
		customerService.deposit(account, amount);
		System.out.println("Deposit successful. New balance: " + account.getBalance());
	}

	public void handleTransfer(Account sourceAccount, Account targetAccount, double amount) {
		try {
			customerService.transfer(sourceAccount, targetAccount, amount);
			System.out.println("Transfer successful. New balance of source account: " + sourceAccount.getBalance());
			System.out.println("New balance of target account: " + targetAccount.getBalance());
		} catch (InsufficientFundsException e) {
			System.out.println("Insufficient funds for transfer. Transfer failed.");
		} catch (AccountNotFoundException e) {
			System.out.println("One of the accounts involved in the transfer was not found.");
		}
	}

	public void handleCheckBalance(Account account) {
		double balance = customerService.checkBalance(account);
		System.out.println("Current balance: " + balance);
	}

	public boolean handleLogin(String username, String password) {
		try {
			boolean loggedIn = customerService.login(username, password);
			if (loggedIn) {
				System.out.println("Login successful.");
			} else {
				System.out.println("Invalid credentials. Login failed.");
			}
			return loggedIn;
		} catch (CredentialException e) {
			System.out.println("Invalid credentials. Login failed.");
			return false;
		}
	}

	//    /////////////////////////////////////////////////////////////////////////////////////

	public void handleAccountCreation(String firstName, String lastName, String address, String emailId,
			String mobileNumber, double initialDeposit, String accountType,
			double overdraftLimit) throws KYCException {
		// Validate KYC details
		boolean kycValid = validateKYCDetails(firstName, lastName, address, emailId, mobileNumber,initialDeposit,accountType,overdraftLimit);
		if (!kycValid) {
			throw new KYCException("KYC verification failed. Invalid KYC details provided.");
		}

		// Generate accountId, customerId, and password
		String accountId = generateAccountId();
		String customerId = generateCustomerId();
		String password = generatePassword();

		// Create new account object
		Account account = new Account(accountId, customerId, initialDeposit, accountType, overdraftLimit);

		// Save account details
		customerService.createAccount(account);

		// Generate and save credentials
		customerService.saveCredentials(accountId, customerId, password);

		// Display account details to the user
		System.out.println("Account created successfully!");
		System.out.println("Account ID: " + accountId);
		System.out.println("Customer ID: " + customerId);
		System.out.println("Password: " + password);
	}

	private boolean validateKYCDetails(String firstName, String lastName, String address, String emailId,
			String mobileNumber,double initialDeposit,String accountType,double overdraftLimit) {
		// Check if any of the fields are empty
		if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || emailId.isEmpty() || mobileNumber.isEmpty() || accountType.isEmpty() ) {
			System.out.println("All KYC details are required.");
			return false;
		}

		// Validate email format
		if (!isValidEmail(emailId)) {
			System.out.println("Invalid email format.");
			return false;
		}

		// Validate mobile number format
		if (!isValidMobileNumber(mobileNumber)) {
			System.out.println("Invalid mobile number format.");
			return false;
		}
		if (!isValidDepositAmount(initialDeposit)) {
			System.out.println("Invalid initial deposit.");
			return false;
		}
		if (!isValidAccountType(accountType)) {
			System.out.println("Invalid account type (current,saving).");
			return false;
		}
		if (!isValidDepositAmount(overdraftLimit)) {
			System.out.println("Invalid overdraft limit.");
			return false;
		}
		
		// If all validations pass, return true
		return true;
	}


	private String generateAccountId() {
		// Generate accountId logic (e.g., using UUID or incrementing counter)
		return "ACC" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}

	private String generateCustomerId() {
		// Generate customerId logic (e.g., using UUID or incrementing counter)
		return "CUST" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}

	private String generatePassword() {
		// Generate password logic (e.g., using random alphanumeric characters)
		return "123";
	}
	
	
	
	
	private boolean isValidEmail(String email) {
	    // Email validation logic
	    // Regular expression pattern for email validation
	    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	    return email.matches(emailRegex);
	}

	private boolean isValidMobileNumber(String mobileNumber) {
	    // Mobile number validation logic
	    // Regular expression pattern for mobile number validation
	    String mobileRegex = "\\d{10}"; // Assuming a 10-digit mobile number
	    return mobileNumber.matches(mobileRegex);
	}
	private boolean isValidDepositAmount(double intitialDeposit) {
		return intitialDeposit>=0;
	}
	private boolean isValidAccountType(String accountType) {
		return (accountType.toLowerCase().equals("saving")||accountType.toLowerCase().equals("current"));
	}
}




