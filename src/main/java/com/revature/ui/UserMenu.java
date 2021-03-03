package com.revature.ui;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.dao.UserDAOImpl;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class UserMenu implements Menu{
	private static Logger log = Logger.getLogger(UserMenu.class);
	public UserService userService = new UserService();
	String otherUser = "";
	User hold;
	double dep = 0;
	double with = 0;
	double send = 0;
	Connection con;
	Transaction act;
	
	public void display(User user) {
		// TODO Auto-generated method stub
		System.out.println("User Menu goes here");
		
		int choice = 0;
		
		do {
			System.out.println("===USER MENU===");
			System.out.println("Please select an option below:");
			System.out.println("1. Back");
			System.out.println("2. Get balance");
			System.out.println("3. Make a deposit");
			System.out.println("4. Make a withdraw");
			System.out.println("5. Send transaction");
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {
			}
			
			switch (choice) {
				case 1:
					//go back to the main menu
					Login mainMenu = new MainMenu();
					mainMenu.display();
					break;
				case 2:	
					//check your balance
					System.out.println("Your balance is :$" + user.getBalance());
					break;
				case 3:
					//deposit money
					do {
					System.out.println("Please enter the amount you wish to deposit:");
					try {
						dep = Double.parseDouble(Menu.sc.nextLine());
					} catch (NumberFormatException e) {
					}
					//check that they aren't trying to deposit negative values
					}while(dep <= 0);
					try (Connection con = ConnectionUtil.getConnection()){
						userService.userDAO.Deposit(user, con, user.getUsername(), dep);
					}catch(SQLException e) {
						System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
					}
					log.info("User " + user.getUsername() + " has deposited $" + dep);
					break;
				case 4:
					//withdraw money
					do {
					System.out.println("Please enter the amount you wish to withdraw:");
					try {
						with = Double.parseDouble(Menu.sc.nextLine());
					} catch (NumberFormatException e) {
					}
					//check that they aren't trying to withdraw more money than they have or a negative value
					}while(with <= 0 || with > user.getBalance());
					try (Connection con = ConnectionUtil.getConnection()){
						userService.userDAO.Withdrawl(user, con, user.getUsername(), with);
					}catch(SQLException e) {
						System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
					}
					log.info("User " + user.getUsername() + " has withdrawn $" + with);
					break;
				case 5:	
					//send a transaction
					//check that the user they are sending to exists
					while(hold == null) {
						System.out.println("Please enter the user you which to send to:");
						otherUser = Menu.sc.nextLine();
						try {
							hold = userService.getUserByUsername(otherUser);
						}catch(UserNotFoundException | SQLException e) {
							System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
							log.error("Sender " + user.getUsername() + " tried sending money to an account that cannot be found: " + otherUser);
							hold = null;
						} 
						}
					//make sure they don't send more money than they have or a negative value
					do {
						System.out.println("Please enter the amount you wish to send:");
						try {
							send = Double.parseDouble(Menu.sc.nextLine());
						} catch (NumberFormatException e) {
						}
						}while(send <= 0 || send > user.getBalance());
						act = new Transaction(user.getUsername(), send, hold.getUsername());
						try (Connection con = ConnectionUtil.getConnection()){
							userService.userDAO.Withdrawl(user, con, user.getUsername(), send);
							userService.userDAO.Deposit(hold, con, hold.getUsername(), send);
							userService.transactionDAO.insertTransaction(act, con, user.getUsername());
						}catch(SQLException e) {
							System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
						}
						log.info("Transaction by " + user.getUsername() + " has been added.");
					break;
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while(choice != 1);
	}

}
