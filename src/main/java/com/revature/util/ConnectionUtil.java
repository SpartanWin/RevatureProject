package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class ConnectionUtil {
	
	private ConnectionUtil() {
		super();
	}
	
	//get the connection to the database setup
	public static Connection getConnection() throws SQLException {
		Driver postgresDriver = new Driver();
		DriverManager.registerDriver(new Driver());
		
		String url = System.getenv("db_url");
		String username = System.getenv("db_username");
		String password = System.getenv("db_password");
		
		Connection connection = DriverManager.getConnection(url,username,password);
		
		return connection;
	}

}
