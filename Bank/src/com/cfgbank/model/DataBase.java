package com.cfgbank.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


//import com.cfgbank.flexcube.model.Account;
//import com.cfgbank.flexcube.model.Credentials;
//import com.cfgbank.flexcube.model.Customer;

public class DataBase {
	static Connection con = null;
	static PreparedStatement psmt = null;
	static Statement stmt = null;

	
//	 this is working outside the current project --working
	
	public static boolean createAccount(Account account) {
		boolean accountAndCredentialsSetSuccessfully = false;
		try {
			createConnection();
			
			String sqlQuery = "INSERT INTO ACCOUNT VALUES(?,?,?,?,?)";
			// Let's create a PrepareStatement object
			psmt = con.prepareStatement(sqlQuery);
			// set the values
			psmt.setInt(1, account.getAccountId());
			psmt.setInt(2, account.getCustomerId());
			psmt.setDouble(3, account.getBalance());
			psmt.setString(4, account.getAccountType());
			psmt.setDouble(5, account.getOverdraftLimit());

			// execute the query
			int result = psmt.executeUpdate();
			if (result > 0) {
				System.out.println("Account successfully created");
			} else {
				System.out.println("Some issues in Account Creation...please check");
			}

			////////////////
			String sqlQuery2 = "INSERT INTO CREDENTIALS VALUES(?,?,?)";
			psmt = con.prepareStatement(sqlQuery2);
			String passwordGenerated = generatePassword();
			// set the values
			psmt.setInt(1, account.getAccountId());
			psmt.setInt(2, account.getCustomerId());
			psmt.setString(3, passwordGenerated);
			int result2 = psmt.executeUpdate();

			if (result2 > 0) {
				System.out.println("Credentials successfully created");
				System.out.println("Use this credentials to connect to your account");
				System.out.println("user name  : " + account.getAccountId() + " password : " + passwordGenerated);
				accountAndCredentialsSetSuccessfully = true;
			} else {
				System.out.println("Some issues in Account Creation...please check");
			}

		} catch (SQLException sqlException) {
			System.out.println(sqlException.getMessage());
		} finally {
			closeConnection();
		}
		return accountAndCredentialsSetSuccessfully;
	}
	
	
	
	
	//add the customer to the data base --working	
	public static customer addACustomer(customer customer) {

		try {
			createConnection();
			
			
			String sqlQuery = "INSERT INTO CUSTOMER VALUES(?,?,?,?,?,?)";
			// Let's create a PrepareStatement object
			psmt = con.prepareStatement(sqlQuery);
			// set the values
			
			psmt.setInt(1, customer.getCustomerId());
			psmt.setString(2, customer.getFirstName());
			psmt.setString(3, customer.getLastName()); 
			psmt.setString(4, customer.getMobileNumber()); 
			psmt.setString(5, customer.getEmailId());
			psmt.setString(6, customer.getAddress()); 
			
			// execute the query
			int recnt = psmt.executeUpdate(); // This will actually execute the query and returns the number of rows
												// modified
			if (recnt > 0) {
				System.out.println("Customer successfully created....");
				return customer;
			} else {
				System.out.println("Some issues in Customer creation ...please check");
			}

		} catch (SQLException sqlException) {
			System.out.println(sqlException.getMessage());
		} finally {
			closeConnection();
		}
		return null;

	}
	
	
	
//	update the balance of an account --working
	public static void updateAccountBalance(Account account) {
		try {

			createConnection();
			String sqlUpdate = "UPDATE ACCOUNT SET ";
			sqlUpdate = sqlUpdate + " ACCOUNTBALANCE=" + account.getBalance() + ",";

			if (sqlUpdate.endsWith(","))
				sqlUpdate = sqlUpdate.substring(0, sqlUpdate.length() - 1);

			sqlUpdate = sqlUpdate + " WHERE ACCOUNTID='" + account.getAccountId()+"'";
			stmt = con.createStatement();
			int rowsModified = stmt.executeUpdate(sqlUpdate);
			if (rowsModified > 0) {
				System.out.println("Account Balance Updated successfully");
			} else {
				System.out.println("Not updated, there is no such account id present " + account.getAccountId());
			}
		} catch (SQLException sqlException) {
			System.out.println(sqlException.getMessage());
		} finally {
			closeConnection();
		}

	}
		
//	used in the login 
	public static Credentials getAccountCredentials(int accountId) {
		try {
			createConnection();
			String sqlQuery = "SELECT * FROM CREDENTIALS WHERE ACCOUNTID = ?";
			// Let's create a PrepareStatement object
			psmt = con.prepareStatement(sqlQuery);
			// set the values
			psmt.setInt(1, accountId); // setting the id

			// execute the query
			ResultSet result = psmt.executeQuery();

			if (result.next()) {
				int accId = result.getInt(1);
				int customerId = result.getInt(2);
				String password = result.getString(3);
				
				return new Credentials(accId, customerId, password);
			}

		} catch (SQLException sqlException) {
			System.out.println(sqlException.getMessage());
		} finally {
			closeConnection();
		}
		return null;
	}

	
	
	
	
//	this is used to find the account to do the transfer of money and also to login
	public static Account findAccountById(int accountId) {
		try {
			createConnection();
			String sqlQuery = "SELECT * FROM ACCOUNT WHERE ACCOUNTID = ?";
			// Let's create a PrepareStatement object
			psmt = con.prepareStatement(sqlQuery);
			// set the values
			psmt.setInt(1, accountId); // setting the id

			// execute the query
			ResultSet result = psmt.executeQuery();
			if (result.next()) {
				int accId = result.getInt(1);
				int customerId = result.getInt(2);
				double accountBalance = result.getDouble(3);

				String accountType = result.getString(4);
				double overdraftLimit = result.getDouble(5);
			    return new Account(accId, customerId, accountBalance,accountType, overdraftLimit);
			}

		} catch (SQLException sqlException) {
			System.out.println(sqlException.getMessage());
		} finally {
			closeConnection();
		}
		return null;
	}

	




	
	public static void createConnection() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			System.out.println("Successfully loaded the oracle jdbc driver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","Hamza_136");
			if (con!=null) {
//				System.out.println("Connection created succesfully");
			} else {
				System.out.println("Connection not created");
			}
		}
		catch(ClassNotFoundException c) {
			System.out.println("The driver class is not found, please check");
		}
		catch(SQLException sql) {
			System.out.println(sql.getMessage());
		}

	}


	public static void closeConnection() {
		if (con != null) {
			try {
				con.close();
//				System.out.println("DB connection closed succesfully");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static String generatePassword() {
		Random r = new Random();
		// return UUID.randomUUID().toString();
		int temp = r.nextInt(100);
		String pass = "pass" + temp;
		return pass;
	}

	
}
