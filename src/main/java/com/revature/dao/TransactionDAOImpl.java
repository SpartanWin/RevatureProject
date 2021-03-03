package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

import java.time.LocalDate;

import com.revature.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {
	private static Logger log = Logger.getLogger(TransactionDAOImpl.class);
	
	Transaction post = new Transaction();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	//switching to sql.Date so it can be uploaded to the table
	Date checkTime = new Date(System.currentTimeMillis());
	
	
	//get all the transactions of one user
	@Override
	public List<Transaction> getTransactionsByUsername(String username, Connection con) throws SQLException {
String sql = "SELECT * FROM project.transactions WHERE username = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, username);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<Transaction> posts = new ArrayList<>();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			Date timeDate = rs.getDate("moneydate");
			String un = rs.getString("username");
			double amount = rs.getDouble("amount");
			String reciever = rs.getString("reciever");
			
			post = new Transaction(id,un,amount,reciever,timeDate);
			
			posts.add(post);
		}
		return posts;
	}
	
	//get all transactions overall
	@Override
	public List<Transaction> getAllTransactions(Connection con) throws SQLException {
String sql = "SELECT * FROM project.transactions";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<Transaction> posts = new ArrayList<>();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			Date timeDate = rs.getDate("moneydate");
			String username = rs.getString("username");
			double amount = rs.getDouble("amount");
			String reciever = rs.getString("reciever");
			
			post = new Transaction(id,username,amount,reciever,timeDate);
			
			posts.add(post);
		}
		return posts;
	}
	
	
	//add transaction to the table
	@Override
	public Transaction insertTransaction(Transaction act, Connection con, String username) throws SQLException {
		String sql = "INSERT INTO project.transactions (moneydate, username, amount, reciever) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setDate(1, checkTime);
		pstmt.setString(2, username);
		pstmt.setDouble(3, act.getAmount());
		pstmt.setString(4, act.getReciever());
		
		int count = pstmt.executeUpdate();
		
		if (count != 1) {
			throw new SQLException("Did not successfully insert Post " + post);
		}
		
		// Retrieve auto generated ID, and set it on our Post object
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			int id = rs.getInt(1);
			post.setId(id);
		}
		return post;
	}

}
