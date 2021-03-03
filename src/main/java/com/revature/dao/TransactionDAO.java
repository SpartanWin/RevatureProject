package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Transaction;

public interface TransactionDAO {
	
	public List<Transaction> getTransactionsByUsername(String username, Connection con) throws SQLException;
	public List<Transaction> getAllTransactions(Connection con) throws SQLException;
	public Transaction insertTransaction(Transaction act, Connection con, String username) throws SQLException;

}
