package com.cfgbank.test;

import static org.junit.Assert.assertNotNull;

import javax.security.auth.login.CredentialException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cfgbank.controller.CustomerController;
import com.cfgbank.model.Account;
import com.cfgbank.model.KYCException;

public class ServicesTest {


	static CustomerController customerController;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		customerController = new CustomerController();
	}

//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		customerService.deposit(20l, "account25");
//		customerService = null;
//	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHandleAccountCreation() throws KYCException {
		Account result = customerController.handleAccountCreation("test1", "test1", "test1", "test1@gmail.com", "1234567890", 100, "saving", 100);
		assertNotNull(result);
	}

	@Test
	public void testHandleLogin() throws CredentialException {
		Account result = customerController.handleLogin(0,"pass65");
		assertNotNull(result);
	}

	@Test
	public void testHandleDeposit() {
		
	    private int accountId;
	    private int customerId;
	    private double balance;
	    private String accountType;
	    private double overdraftLimit;
		Account account =  Account(0,1,380.0,"saving",100.0);
		Customer result = customerController.handleDeposit(account, amount);;
		assertNotNull(result);
	}

	@Test
	public void testGenerateAccountNumber() {
		int result = customerService.generateAccountNumber();
		assertNotNull(result);
		assertTrue(result <= 100);
	}

	@Test(expected = InvalidCredentialException.class)
	public void testLoginAccountNotFound() throws Exception {
		customerService.login("122122", "xvx255222");
	}

	@Test(expected = InvalidCredentialException.class)
	public void testLoginAccountWrongPassword() throws Exception {
		customerService.login("account25", "xvx255222");
	}

	@Test
	public void testLoginAccount() throws Exception {
		boolean isLogged = customerService.login("account25", "pass17");
		assertTrue(isLogged);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testDeposit() throws Exception {
		double oldBalance = customerService.checkBalance("account25");
		double result = customerService.deposit(1000, "account25");
		assertEquals((oldBalance + 1000), result,0.0001);

	}

	@Test(expected = KYCException.class)
	public void testDepositAmountNegatif() throws Exception {
		customerService.deposit(-122, "account25");

	}

	@Test(expected = AccountNotFoundException.class)
	public void testDepositWithNonExistingAccount() throws Exception {
		customerService.deposit(1000, "account55555");

	}

	@Test
	public void testTransfer() throws Exception {
		double targetOldalance = customerService.checkBalance("account94");
		customerService.transfer("account25", "account94", 1000);
		double targetNewBalance = customerService.checkBalance("account94");

		assertTrue(targetNewBalance == (targetOldalance + 1000));
	}

	@Test
	public void testCheckBalance() throws Exception {
		double balance = customerService.checkBalance("account25");
		assertTrue(balance >= 0l);
	}

	@Test(expected = InsufficientFundsException.class)
	public void testWithdrawInsufficientBalance() throws Exception {
		customerService.withdraw("account25", Double.MAX_VALUE);
	}

	@SuppressWarnings("deprecation")
	@Test()
	public void testWithdraw() throws Exception {
		double oldBalance = customerService.checkBalance("account25");
		customerService.withdraw("account25", 20l);
		double newBalance = customerService.checkBalance("account25");
		assertEquals((oldBalance - 20l), newBalance,0.0001);
	}

	@Test(expected = AccountNotFoundException.class)
	public void testFindAccountByIdNotFound() throws Exception {
		customerService.findAccountById("sf2222");
	}

	@Test
	public void testFindAccountById() throws Exception {
		Account account = customerService.findAccountById("account25");
		assertNotNull(account);
		assertEquals("account25", account.getAccountId());

	}

	@Test
	public void testValidCustomerAccountCreationInputNotValid() {
		boolean isValid = customerService.validCustomerAccountCreationInput("dsfs", "dsfe", "sfsd", "sfsf", 06161616l);
		assertFalse(isValid);
	}

	@Test
	public void testValidCustomerAccountCreationInputValid() {
		boolean isValid = customerService.validCustomerAccountCreationInput("dsfs", "dsfe",
				"sfsd", "sfsf",5646464646l);
		assertTrue(isValid);
	}

}

