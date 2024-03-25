package com.cfgbank.model;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Insufficient funds in the account");
    }
}
