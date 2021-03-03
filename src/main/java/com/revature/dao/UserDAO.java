package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Transaction;
import com.revature.model.User;

public interface UserDAO {
	
	User getUserByUsername(String username, Connection con) throws SQLException;
	User insertApplicant(User user, Connection con, String username) throws SQLException;
	User Withdrawl(User user, Connection con, String username, double amount) throws SQLException;
	User Deposit(User user, Connection con, String username, double amount) throws SQLException;
	User insertUser(User user, Connection con, String username) throws SQLException;
	User deleteApplicant(User user, Connection con, String username) throws SQLException;
	List<User> getAllApplicants(Connection con) throws SQLException;

}
