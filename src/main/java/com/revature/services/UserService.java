package com.revature.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.dao.TransactionDAO;
import com.revature.dao.TransactionDAOImpl;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class UserService {
	
	public UserDAO userDAO; 
	public TransactionDAO transactionDAO;
	
	//Let the ui access all the various DAO methods
	public UserService() {
		this.userDAO = new UserDAOImpl();
		this.transactionDAO = new TransactionDAOImpl();
	}
	
	//get users and their transactions
	public User getUserByUsername(String username) throws UserNotFoundException, SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			User user;
			
			user = userDAO.getUserByUsername(username, con);
			
			if (user == null) {
				throw new UserNotFoundException("User with username '" + username + "' was not found!");
			}
			
			List<Transaction> userPosts = transactionDAO.getTransactionsByUsername(username, con);
			
			user.setTranscations(userPosts);
			
			return user;
		}
	}

}
