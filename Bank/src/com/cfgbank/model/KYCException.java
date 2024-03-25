package com.cfgbank.model;

public class KYCException extends Exception {
	public  KYCException() {
		super("Error in the values");
	}

	public KYCException(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}

}
