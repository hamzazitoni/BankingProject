package com.cfgbank.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
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

}
