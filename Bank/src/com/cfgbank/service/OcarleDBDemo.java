package com.cfgbank.service;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OcarleDBDemo {

	static Connection con=null;
	static PreparedStatement psmt=null;
	static Statement stmt=null;

	public static void createConnection() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Successfully loaded the oracle jdbc driver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","Hamza_136");
			if (con!=null) {
				System.out.println("Connection created succesfully");
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

	public static void closeConnection()  {

		try {
			if (con!=null) {
				con.close();
				System.out.println("DB connection closed succesfully");
			}
		}
		catch (SQLException cl) {
			System.out.println("DB connection closing issues "+cl.getMessage());
		}
	}

//	public static void updateACustomer(int custId, Customer customer) throws SQLException {
//		createConnection();
//		String sqlUpdate="UPDATE CUSTOMER SET ";
//		if (customer.getFirstName()!=null) {
//			sqlUpdate=sqlUpdate+" FIRST_NAME="+"'"+customer.getFirstName()+"'" +",";
//		}
//		
//		if (customer.getLastName()!=null) {
//			sqlUpdate=sqlUpdate+" LAST_NAME="+"'"+customer.getLastName()+"'"+",";
//		}
//		
//		if (customer.getAge()!=0) {
//			sqlUpdate=sqlUpdate+" AGE="+customer.getAge();
//		}
//		if (sqlUpdate.endsWith(","))
//			sqlUpdate=sqlUpdate.substring(0, sqlUpdate.length()-1);
//		
//		sqlUpdate=sqlUpdate+" WHERE ID="+custId;
//		System.out.println(sqlUpdate);
//		stmt=con.createStatement();
//		int rowsModified=stmt.executeUpdate(sqlUpdate);
//		if (rowsModified>0) {
//			System.out.println("Updated successfully");
//		} else {
//			System.out.println("Not updated, there is no such id present "+custId);
//		}
//			
//		closeConnection();
//		
//	}
	
	
	public static void deleteACustomer(int custId) throws SQLException {
		// Implement this
		System.out.println("Delete method");

		try {
			createConnection();
			String deleteSQL="DELETE FROM CUSTOMER WHERE ID=?";
			psmt=con.prepareStatement(deleteSQL);
			psmt.setInt(1, custId);
			int rowsDeleted=psmt.executeUpdate();
			if (rowsDeleted>0)
				System.out.println("The customer with id "+custId+" has been deleted successfully");
			else 
				System.out.println("Not deleted, becasue no customer id "+custId);

		}
		catch(SQLException sqlException) {
			System.out.println("Not deleted, see the reason below "+sqlException.getCause());
			System.out.println(sqlException.getMessage());
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		finally {
			closeConnection();
		}
	}


	public static void addACustomer() throws SQLException {
		System.out.println("Add customer method");
		try {
			createConnection();
			String sqlQuery="INSERT INTO CUSTOMER VALUES(?,?,?,?,?,?,?,?,?,?)";
			// Let's create a PrepareStatement object
			psmt=con.prepareStatement(sqlQuery);
			// set the values
			psmt.setInt(1, 5); // setting the id
			psmt.setString(2, "Bull"); // setting the first name
			psmt.setString(3, "Gates"); // setting the last name
			psmt.setString(4, "Peters's avenue"); // setting the address
			psmt.setString(5, "M"); // setting the gender
			psmt.setLong(6, 1929292929L); // setting the mobile
			psmt.setString(7, "bill@msn.com"); // setting the email id
			psmt.setLong(8,123848L); // setting the rnp number
			psmt.setString(9, "xyz929");// setting the cin number
			psmt.setInt(10, 62);// setting the age
			// excute the query
			int recnt=psmt.executeUpdate(); // This will actually execute the query and returns the number of rows modified
			if (recnt>0) {
				System.out.println("Sucessfully inserted....");
			} else {
				System.out.println("Some issues...please check");
			}


		}
		catch(SQLException sqlException) {
			System.out.println(sqlException.getMessage());
		}
		finally {
			closeConnection();
		}

	}


	public static void fetchAllCustomers() throws Exception{
		System.out.println("Fetch All customers");
		try {
			createConnection();
			stmt=con.createStatement();
			ResultSet resultSet=stmt.executeQuery("SELECT * FROM CUSTOMER");
			// This loop will run for 'n' rows from the db tables
			System.out.println("-----------------------------------------------");
			while (resultSet.next()) {
				// assign these values to local variables
				int id=resultSet.getInt(1);
				String firstName=resultSet.getString(2);
				String lastName=resultSet.getString(3);
				String address=resultSet.getString(4);
				String gender=resultSet.getString(5);
				long mobileNumber=resultSet.getLong(6);
				String emailId=resultSet.getString(7);
				long rnpNumber=resultSet.getLong(8);
				String cinNumber=resultSet.getString(9);
				int age=resultSet.getInt(10);
				// Do whatever you want
				System.out.println(id+" "+firstName+","+lastName+","+address+"-"+gender+","+mobileNumber+"-"+emailId+"-"+rnpNumber+","+cinNumber+","+age);

			}

			System.out.println("-----------------------------------------------");

		}
		catch(SQLException sqlExeption) {
			System.out.println(sqlExeption.getMessage());



		} 

		finally {
			// This will be called always, it is better to write clean up code
			closeConnection();
		}

	}


//	public static void main(String[] args) throws Exception{
//
////		fetchAllCustomers();
////		addACustomer();
////		deleteACustomer(1);
//		Customer cust=new Customer();
//		cust.setId(2);
//		cust.setFirstName("GEORGE");
//		cust.setLastName("BUSH");
//		cust.setAge(72);
//		updateACustomer(2, cust);
//		
//	}

}
