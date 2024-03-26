package com.cfgbank.view;

import java.util.Scanner;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;

import com.cfgbank.controller.CustomerController;
import com.cfgbank.model.Account;
import com.cfgbank.model.DataBase;
import com.cfgbank.model.InsufficientFundsException;
import com.cfgbank.model.KYCException;
import com.cfgbank.service.CustomerService;

public class CustomerView {
	
    private CustomerController customerController;
    
    public CustomerService customerService;
    
//    public static Account account ;

    public CustomerView(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void start() throws AccountNotFoundException, InsufficientFundsException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Banking Application");
        System.out.println("1. Create Account");
        System.out.println("2. Login");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Handle account creation
            	 handleAccountCreation(scanner);
                break;
            case 2:
			try {
				handleLogin(scanner);
			} catch (AccountNotFoundException | InsufficientFundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            default:
                System.out.println("Invalid choice");
        }
        
    }
    
    private void handleAccountCreation(Scanner scanner) throws AccountNotFoundException, InsufficientFundsException {
        System.out.println("Enter first name:");
        String firstName = scanner.next();
        System.out.println("Enter last name:");
        String lastName = scanner.next();
        System.out.println("Enter address:");
        String address = scanner.next();
        System.out.println("Enter email id:");
        String emailId = scanner.next();
        System.out.println("Enter mobile number:");
        String mobileNumber = scanner.next();
        System.out.println("Enter initial deposit amount:");
        double initialDeposit = scanner.nextDouble();
        System.out.println("Enter account type:");
        String accountType = scanner.next();
        System.out.println("Enter overdraft limit:");
        double overdraftLimit = scanner.nextDouble();

        // Assuming you have a method in CustomerController to handle account creation
        try {
            Account loggedInAccount = customerController.handleAccountCreation(firstName, lastName, address, emailId, mobileNumber,
                    initialDeposit, accountType, overdraftLimit);
//        	 customerController.handleAccountCreation("hamza", "zitouni", "Address", "test@gmail.com", "1234567890",100, "saving", 100);
//            System.out.println("Account created successfully!");
            // Bank operations	 
            System.out.println("---------------------Login successful. Welcome!------------------");
            while(true) {
            	
	        	Operations(loggedInAccount);
	        }
	      
        } catch (KYCException e) {
//            System.out.println("KYC verification failed. Account creation failed.");
            e.getMessage();
        }
    }

    private void handleLogin(Scanner scanner) throws AccountNotFoundException, InsufficientFundsException {
        System.out.println("Enter username:");
        int username = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        // Assuming you have a method in CustomerController to handle login
        Account loggedInAccount = null;
		try {
			loggedInAccount = customerController.handleLogin(username, password);
		} catch (CredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (loggedInAccount != null) {
            // Redirect to user dashboard or provide options for banking operations
//        	account=customerService.searchAccount(customerService.getaccountList(),username);
	        // Bank operations	
            System.out.println("---------------------Login successful. Welcome!------------------");
	        while(true) {
	        	Operations(loggedInAccount);
	        }
          

	        	
        } else {
            // Handle login failure
            System.out.println("Login failed. Please check your credentials.");
        }
    }
    
    
    public void Operations(Account loggedInAccount) throws InsufficientFundsException, AccountNotFoundException {
    	
    	
    	Scanner scanner = new Scanner(System.in); 
    	
    	System.out.println(".");
        System.out.println("Please choose next operation:");
        System.out.println("1:Deposit");
        System.out.println("2:Withdraw");
        System.out.println("3:Tranfer");
        System.out.println("4:CheckBalance");
        System.out.println(".");
        int choice = scanner.nextInt();
        
        switch (choice) {
			case 1: {
				System.out.println("How much you want to deposit in your account:");
				double depo=scanner.nextDouble();
				customerController.handleDeposit(loggedInAccount,depo);
				break;
			}
			case 2:{
				System.out.println("How much you want to withdrow from your account:");
				double wdraw=scanner.nextDouble();
				customerController.handleWithdraw(loggedInAccount,wdraw);
				break;
			}
			case 3:{
				System.out.println("Enter the Id of the reciver account:");
				int reciverId=scanner.nextInt();
				System.out.println("How mush you  want to transfer:");
				double amount=scanner.nextDouble();
				//khassni n9aleb 3la who men accountList 
				//db ghadi njareb ghir bi account li 3andi 
				customerController.handleTransfer(loggedInAccount.getAccountId(), reciverId, amount);
				break;
			}
			case 4:{
				
				customerController.handleCheckBalance(loggedInAccount);
				break;
			}
			default:
				//throw new IllegalArgumentException("Unexpected value: " + choice);
				System.out.println("Option invalid, Exiting ! ");
			}
    }

}