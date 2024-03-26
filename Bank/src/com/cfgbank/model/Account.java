package com.cfgbank.model;

public class Account {
    private int accountId;
    private int customerId;
    private double balance;
    private String accountType;
    private double overdraftLimit;
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Account(int accountId, int customerId, double balance, String accountType, double overdraftLimit) {
		super();
		this.accountId = accountId;
		this.customerId = customerId;
		this.balance = balance;
		this.accountType = accountType;
		this.overdraftLimit = overdraftLimit;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getOverdraftLimit() {
		return overdraftLimit;
	}
	public void setOverdraftLimit(double overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", customerId=" + customerId + ", balance=" + balance
				+ ", accountType=" + accountType + ", overdraftLimit=" + overdraftLimit + "]";
	}
    
    
}
