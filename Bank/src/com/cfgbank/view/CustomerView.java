package com.cfgbank.view;

import java.util.Scanner;

import com.cfgbank.controller.CustomerController;
import com.cfgbank.model.Account;
import com.cfgbank.model.KYCException;
import com.cfgbank.service.CustomerService;

public class CustomerView {
	
    private CustomerController customerController;
    
    public CustomerService customerService;
    
    public static Account account ;

    public CustomerView(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Banking Application");
        System.out.println("1. Create Account");
        System.out.println("2. Login");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Handle account creation
            	 handleAccountCreation(scanner);
            	 handleLogin(scanner);
                break;
            case 2:
                // Handle login
            	handleLogin(scanner);
                break;
            default:
                System.out.println("Invalid choice");
        }
        
    }
    
    private void handleAccountCreation(Scanner scanner) {
//        System.out.println("Enter first name:");
//        String firstName = scanner.next();
//        System.out.println("Enter last name:");
//        String lastName = scanner.next();
//        System.out.println("Enter address:");
//        String address = scanner.next();
//        System.out.println("Enter email id:");
//        String emailId = scanner.next();
//        System.out.println("Enter mobile number:");
//        String mobileNumber = scanner.next();
//        System.out.println("Enter initial deposit amount:");
//        double initialDeposit = scanner.nextDouble();
//        System.out.println("Enter account type:");
//        String accountType = scanner.next();
//        System.out.println("Enter overdraft limit:");
//        double overdraftLimit = scanner.nextDouble();

        // Assuming you have a method in CustomerController to handle account creation
        try {
//            customerController.handleAccountCreation(firstName, lastName, address, emailId, mobileNumber,
//                    initialDeposit, accountType, overdraftLimit);
        	 customerController.handleAccountCreation("hamz", "zitouni", "Address", "test@gmail.com", "1234567890",
                     100, "saving", 100);
            System.out.println("Account created successfully!");
            

            
        } catch (KYCException e) {
            System.out.println("KYC verification failed. Account creation failed.");
        }
    }

    private void handleLogin(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        // Assuming you have a method in CustomerController to handle login
        boolean loggedIn = customerController.handleLogin(username, password);
        if (loggedIn) {
            // Redirect to user dashboard or provide options for banking operations
        	account=customerService.searchAccount(customerService.getaccountList(),username);
	        System.out.println("Login successful. Welcome!");
	        
	        System.out.println("Please chose next operation:");
	        System.out.println("1:Deposet");
	        System.out.println("2:Withdraw");
	        System.out.println("3:Tranfer");
	        System.out.println("4:CheckBalance");
	        int choice = scanner.nextInt();
          
	          switch (choice) {
				case 1: {
					System.out.println("how much?????");
					double depo=scanner.nextDouble();
					customerController.handleDeposit(account,depo);
				}
				case 2:{
					System.out.println("how much?????");
					double wdraw=scanner.nextDouble();
					customerController.handleWithdraw(account,wdraw);
				}
				case 3:{
					System.out.println("how much?????");
					double wdraw=scanner.nextDouble();
					System.out.println("for whoo?????");
					String who=scanner.nextLine();
					//khassni n9aleb 3la who men accountList 
					//db ghadi njareb ghir bi account li 3andi 
					customerController.handleTransfer(account, account, choice);
				}
				case 5:{
					customerController.handleCheckBalance(account);
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + choice);
				}
	        	
        } else {
            // Handle login failure
            System.out.println("Login failed. Please check your credentials.");
        }
    }

}