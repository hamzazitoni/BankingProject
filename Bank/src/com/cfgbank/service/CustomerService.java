package com.cfgbank.service;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;

import com.cfgbank.model.Account;
import com.cfgbank.model.Credentials;
import com.cfgbank.model.InsufficientFundsException;

public class CustomerService {
	
	
    private List<Account> accountList= new ArrayList<>(); // Assume this is initialized with existing accounts
    private List<Credentials> credentialsList = new ArrayList<>(); // Assume this is initialized with existing credentials

    public void withdraw(Account account, double amount) throws InsufficientFundsException {
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
    }

    public void deposit(Account account, double amount) {
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
    }

    public void transfer(Account sourceAccount, Account targetAccount, double amount)
            throws InsufficientFundsException, AccountNotFoundException {
        if (!accountList.contains(sourceAccount) || !accountList.contains(targetAccount)) {
            throw new AccountNotFoundException();
        }
        if (sourceAccount.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        withdraw(sourceAccount, amount);
        deposit(targetAccount, amount);
    }

    public double checkBalance(Account account) {
        return account.getBalance();
    }

    public boolean login(String username, String password) throws CredentialException {
        for (Credentials credentials : credentialsList) {
            if (credentials.getCustomerId().equals(username) && credentials.getPassword().equals(password)) {
                return true; // Successful login
            }
        }
        throw new CredentialException();
    }
    
    
    public void createAccount(Account account) {
        accountList.add(account);
        credentialsList.add(new Credentials(account.getCustomerId(),account.getAccountId(),"123"));
        
        System.out.println("Account details saved successfully!");
    }
    public void saveCredentials(String accountId, String customerId,String password) {
    	credentialsList.add(new Credentials(accountId,customerId,password));
    	
    }
    
    public ArrayList<Account> getaccountList(){
    	return (ArrayList<Account>) accountList;
    }
    
    public Account searchAccount(ArrayList<Account>AccountList, String username) {
    	for( Account account:AccountList) {
    		if(account.getCustomerId().equals(username))
    			return account;
    	}
    	
    return null;
    	
    }
    
}