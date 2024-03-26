package com.cfgbank.controller;

import java.util.Random;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;

import com.cfgbank.model.Account;
import com.cfgbank.model.Credentials;
import com.cfgbank.model.DataBase;
import com.cfgbank.model.InsufficientFundsException;
import com.cfgbank.model.KYCException;
import com.cfgbank.model.customer;
import com.cfgbank.service.CustomerService;

public class CustomerController {
	DataBase db = new DataBase();
	
	 private static final int MIN_ACCOUNT_ID = 1000;
	 private static final int MAX_ACCOUNT_ID = 9999;

	public CustomerController(CustomerService customerService) {
	}

	
	
public CustomerController() {
		// TODO Auto-generated constructor stub
	}



	//	this handles the account creation and the customer and also Credentials --working
	public Account handleAccountCreation(String firstName, String lastName, String address, String emailId,
			String mobileNumber, double initialDeposit, String accountType,
			double overdraftLimit) throws KYCException {
		// Validate KYC details
		boolean kycValid = validateKYCDetails(firstName, lastName, address, emailId, mobileNumber,initialDeposit,accountType,overdraftLimit);
		if (!kycValid) {
			throw new KYCException("KYC verification failed. Invalid KYC details provided.");
		}

		// Generate accountId, customerId, and password
		int accountId = generateAccountId();
		int customerId = generateCustomerId();

		// Create new account object
		
//		
		Account account = new Account(accountId, customerId, initialDeposit, accountType, overdraftLimit);

		// Save account details
		DataBase.createAccount(account);
		
		customer cust = new customer(customerId,firstName,lastName,address,emailId,mobileNumber);
		DataBase.addACustomer(cust);
		
		return account;
	}
	
	
// this is to handle the login using an account id and password
	public Account handleLogin(int accountId, String password) throws CredentialException {		
//		Account account = null;
		
			Credentials creds = DataBase.getAccountCredentials(accountId);
			
			if(creds != null && creds.getPassword().equals(password) ) {
				
				//we use findAccountById() methode to find the account 
				Account account = DataBase.findAccountById(accountId);
				System.out.println("Login successful.");
				return account;
			}else
				throw new CredentialException();

		
	}

	
//	this is just basically deposit an amount in the account --working
	public void handleDeposit(Account account, double amount) {
		//we have to set the new amount to the account and update it 
		double result = account.getBalance() + amount ;
		account.setBalance(result);
		DataBase.updateAccountBalance(account);
	}
	
	
	
// handling the withdraw of money --working 
	public void handleWithdraw(Account account, double amount) throws InsufficientFundsException {
		double result = account.getBalance() - amount ;
		account.setBalance(result);
		DataBase.updateAccountBalance(account);
	}



	public void handleTransfer(int sourceAccountId, int targetAccountId, double amount) throws AccountNotFoundException {
		try {
			
			Account sourceAccount= DataBase.findAccountById(sourceAccountId);
			Account targetAccount= DataBase.findAccountById(targetAccountId);
			handleWithdraw(sourceAccount,amount);
			handleDeposit(targetAccount,amount);
			
			System.out.println("Transfer successful. New balance of source account: " + sourceAccount.getBalance());
			System.out.println("New balance of target account: " + targetAccount.getBalance());
		} catch (InsufficientFundsException e) {
			System.out.println("Insufficient funds for transfer. Transfer failed.");
		}
	}

	public void handleCheckBalance(Account account) throws AccountNotFoundException {
		Account acc = DataBase.findAccountById(account.getAccountId());
		System.out.println("Current balance: " + acc.getBalance());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//    /////////////////////////////////////////////////////////////////////////////////////



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


	private int generateAccountId() {
		Random random = new Random();
        return random.nextInt((MAX_ACCOUNT_ID - MIN_ACCOUNT_ID) + 1) + MIN_ACCOUNT_ID;
	}

	private int generateCustomerId() {
		Random random = new Random();
        return random.nextInt((MAX_ACCOUNT_ID - MIN_ACCOUNT_ID) + 1) + MIN_ACCOUNT_ID;
	}

//	private String generatePassword() {
//		// Generate password logic (e.g., using random alphanumeric characters)
//		return "123";
//	}
	
	
	
	
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




