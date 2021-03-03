package com.revature.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.TransactionDAO;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class UserServiceTest {
	
public UserService userService;
	
	private static Logger log = Logger.getLogger(UserDAOImpl.class);
	public static UserDAO userDAO;
	public static TransactionDAO tranDAO;
	Date timeNow = Calendar.getInstance().getTime();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	String strDate = dateFormat.format(timeNow);
	Connection con;
	List<Transaction> transactions = new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Create fake objects (mock objects)
		userDAO = mock(UserDAO.class);
		tranDAO = mock(TransactionDAO.class);
		
		
		// Instruct the methods from these fake objects on what to return when certain parameters are passed into them
		// utilize matchers from org.mockito.ArgumentMatchers
		when(userDAO.getUserByUsername(eq("abc123"), any(Connection.class))).thenReturn(new User("abc123", "Jane", "Doe", "$Ngj1uMK!", 3000.0, false));
		Date timeNow = Calendar.getInstance().getTime();
		List<Transaction> posts = new ArrayList<>();
		posts.add(new Transaction(1, "abc123",10.0,"hello123",timeNow));
		posts.add(new Transaction(1, "abc123",20.0,"hello123",timeNow));
		when(tranDAO.getTransactionsByUsername(eq("abc123"), any(Connection.class))).thenReturn(posts);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Inject the fake objects into the actual UserService object being created
		userService = new UserService();
	}

	@After
	public void tearDown() throws Exception {
	}

	// Actually test those methods
	@Test
	public void testValidUserName() throws UserNotFoundException, SQLException {
		User actual = userService.getUserByUsername("abc123");
		double check = 3000;
		User expected = new User("abc123", "Jane", "Doe", "$Ngj1uMK!", check, false);
		List<Transaction> posts = new ArrayList<>();
		posts.add(new Transaction(1, "abc123",20.0,"hello123",timeNow));
		expected.setTranscations(posts);
		
		assertEquals(expected.getUsername(), actual.getUsername());
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testInvalidUserName() throws UserNotFoundException, SQLException {
		User actual = userService.getUserByUsername("abc1234");
	}
	
	@Test
	public void testInsertApp() throws UserNotFoundException, SQLException {
		User actual = new User("testing", "Test", "App", "test", 0, false);
		userService.userDAO.insertApplicant(actual, con = ConnectionUtil.getConnection(), actual.getUsername());
	}
	
	@Test
	public void testInsertUser() throws UserNotFoundException, SQLException {
		User actual = new User("testing2", "Test", "User", "test", 0, false);
		userService.userDAO.insertUser(actual, con = ConnectionUtil.getConnection(), actual.getUsername());
	}
	
	@Test
	public void testGetAll() throws UserNotFoundException, SQLException {
		transactions = userService.transactionDAO.getAllTransactions(con = ConnectionUtil.getConnection());
	}
	
	@Test
	public void testGetSpecific() throws UserNotFoundException, SQLException {
		transactions = userService.transactionDAO.getTransactionsByUsername("abc123", con = ConnectionUtil.getConnection());
	}
	
	@Test
	public void testWithdraw() throws UserNotFoundException, SQLException {
		User actual = userService.getUserByUsername("abc123");
		double expected = actual.getBalance() - 20;
		userService.userDAO.Withdrawl(actual, con = ConnectionUtil.getConnection(), actual.getUsername(), 20.0);
	}
	
	@Test
	public void testDeposit() throws UserNotFoundException, SQLException {
		User actual = userService.getUserByUsername("abc123");
		double expected = actual.getBalance() + 20;
		userService.userDAO.Deposit(actual, con = ConnectionUtil.getConnection(), actual.getUsername(), 20.0);
	}
	
	@Test
	public void testDeleteApp() throws UserNotFoundException, SQLException {
		User actual = new User("testing", "Test", "App", "test", 0, false);
		userService.userDAO.deleteApplicant(actual, con = ConnectionUtil.getConnection(), actual.getUsername());
	}
	
	@Test
	public void testInsertTransaction() throws UserNotFoundException, SQLException {
		User test = new User("testUser", "Test", "App", "test", 0, false);
		Transaction actual = new Transaction(1, "testUser",20.0,"hello123",timeNow);
		userService.transactionDAO.insertTransaction(actual, con = ConnectionUtil.getConnection(), test.getUsername());
	}
	


}
