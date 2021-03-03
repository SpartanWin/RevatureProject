package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.revature.model.Transaction;
import com.revature.model.User;
import com.sun.tools.sjavac.Log;

public class UserDAOImpl implements UserDAO{
	

	//get user
	@Override
	public User getUserByUsername(String username, Connection con) throws SQLException {
		User user = null;
		
		String sql = "SELECT * FROM project.users WHERE username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, username);
		
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			String un = rs.getString("username");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String pass = rs.getString("pass");
			boolean employee = rs.getBoolean("employee");
			double balance = rs.getDouble("balance");
			
			user = new User(un, firstName, lastName, pass, balance, employee);
		}
		
		return user;
	}
	
	//add user to the table
	@Override
	public User insertUser(User user, Connection con, String username) throws SQLException {
		String sql = "INSERT INTO project.users (username, first_name, last_name, pass, employee, balance) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setString(1, username);
		pstmt.setString(2, user.getFirstName());
		pstmt.setString(3, user.getLastName());
		pstmt.setString(4, user.getPassword());
		pstmt.setBoolean(5, user.isEmployee());
		pstmt.setDouble(6, user.getBalance());
		
		int count = pstmt.executeUpdate();
		
		if (count != 1) {
			throw new SQLException("Did not successfully insert User " + username);
		}
		return user;
	}
	
	//add applicant to the table
	@Override
	public User insertApplicant(User user, Connection con, String username) throws SQLException {
		String sql = "INSERT INTO project.holding (username, first_name, last_name, pass, employee, balance) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setString(1, username);
		pstmt.setString(2, user.getFirstName());
		pstmt.setString(3, user.getLastName());
		pstmt.setString(4, user.getPassword());
		pstmt.setBoolean(5, user.isEmployee());
		pstmt.setDouble(6, user.getBalance());
		
		int count = pstmt.executeUpdate();
		
		if (count != 1) {
			throw new SQLException("Did not successfully insert Applicant " + username);
		}
		return user;
	}
	
	//remove applicant from the table
	@Override
	public User deleteApplicant(User user, Connection con, String username) throws SQLException {
		String sql = "DELETE FROM project.holding WHERE username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setString(1, username);
		
		int count = pstmt.executeUpdate();
		
		if (count != 1) {
			throw new SQLException("Did not successfully insert Applicant " + username);
		}
		
		return user;
	}
	
	//update a user's balance based on their withdrawal
	@Override
	public User Withdrawl(User user, Connection con, String username, double amount) throws SQLException {
		String sql = "UPDATE project.users SET balance = ? WHERE username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setDouble(1, user.getBalance() - amount);
		pstmt.setString(2, username);
		
		int count = pstmt.executeUpdate();
		
		if (count != 1) {
			throw new SQLException("Did not successfully withdraw money.");
		}
		return user;
	}
	
	//update a user's balance based on their deposit
	@Override
	public User Deposit(User user, Connection con, String username, double amount) throws SQLException {
		String sql = "UPDATE project.users SET balance = ? WHERE username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setDouble(1, user.getBalance() + amount);
		pstmt.setString(2, username);
		
		int count = pstmt.executeUpdate();
		
		if (count != 1) {
			throw new SQLException("Did not successfully deposit money.");
		}
		return user;
	}
	
	//get all the applicants from the table
	@Override
	public List<User> getAllApplicants(Connection con) throws SQLException {
		String sql = "SELECT * FROM project.holding";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<User> holds = new ArrayList<>();
		User hold = null;
		
		while (rs.next()) {
			String un = rs.getString("username");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String pass = rs.getString("pass");
			boolean employee = rs.getBoolean("employee");
			double balance = rs.getDouble("balance");
			
			hold = new User(un, firstName, lastName, pass, balance, employee);
			
			holds.add(hold);
		}
		
		return holds;
	}

}
