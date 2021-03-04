package com.revature.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.UserDAOImpl;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Transaction;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class EmployeeMenu implements Menu {
	private static Logger log = Logger.getLogger(UserDAOImpl.class);
	User hold = null;
	public UserService userService = new UserService();
	List<User> applicants = new ArrayList<>();
	List<Transaction> transactions = new ArrayList<>();
	Connection con;
	
	public void display(User user) {
		String usernameAttempt = "";
		String passwordAttempt = "";
		System.out.println("Welcome to the bank app");
		
		int choice = 0;
		int applicantChoice = 0;
		
		do {
			System.out.println("===EMPLOYEE MENU===");
			System.out.println("Please select an option below:");
			System.out.println("1. Return to Main Menu");
			System.out.println("2. See all Transactions");
			System.out.println("3. Check for account approvals");
			System.out.println("4. Look for account balance");
			
			try{
				choice = Integer.parseInt(Menu.sc.nextLine());
			}catch(NumberFormatException e) {
				
			}
			
			switch(choice) {
			case 1:
				//send them back to the main menu
				Login mainMenu = new MainMenu();
				mainMenu.display();
				break;
			case 2:
				//see all transactions overall
				try {
					transactions = userService.transactionDAO.getAllTransactions(con = ConnectionUtil.getConnection());
				}catch(SQLException e) {
					System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
				}
				for(int i = 0; i < transactions.size(); i++ )
				{
					Transaction place = transactions.get(i);
					System.out.println(place.toString());
				}
				log.info("All the transactions have been retrieved.");
				break;
			case 3:
				//check for applicants for approval
				try (Connection con = ConnectionUtil.getConnection()){
					applicants = userService.userDAO.getAllApplicants(con);
				}catch(SQLException e) {
					System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
				}
				
				if(applicants.size() == 0) {
					System.out.println("No new applicants");
				}
				//cycle through all applicants
				for(int i = 0; i < applicants.size(); i++ )
				{
					do {
					int present = i+1;
					User place = applicants.get(i);
					applicantChoice = 0;
					System.out.println("Applicant " + present + ": " + place.toString());
					System.out.println("Accept or Decline");
					System.out.println("1. Accept");
					System.out.println("2. Decline");
					try{
						applicantChoice = Integer.parseInt(Menu.sc.nextLine());
					}catch(NumberFormatException e) {
						
					}
					switch(applicantChoice) {
					case 1:
						//approve an applicant
						try (Connection con = ConnectionUtil.getConnection()){
							hold = userService.userDAO.insertUser(place, con, place.getUsername());
							log.info("User " + place.getUsername() + " has been added.");
							log.info("Applicant " + place.getUsername() + " has been deleted.");
							hold = userService.userDAO.deleteApplicant(place, con, place.getUsername());
						}catch(SQLException e) {
							System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
						}
						break;
					case 2:
						//decline an applicant
						try (Connection con = ConnectionUtil.getConnection()){
							log.info("Applicant " + i + ": " + place.getUsername() + " has been deleted.");
							hold = userService.userDAO.deleteApplicant(place, con, place.getUsername());
						}catch(SQLException e) {
							System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
						}
						break;
					default:
						System.out.println("No valid choice entered. Please try again");
					}
					}while(applicantChoice == 0);
				}
				break;
			case 4:
				//get a user's balance
				while(hold == null) {
					System.out.println("Please enter the account you want to look up:");
					usernameAttempt = Menu.sc.nextLine();
					try {
						hold = userService.getUserByUsername(usernameAttempt);
					}catch(UserNotFoundException | SQLException e) {
						System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
						hold = null;
					} 
					}
					System.out.println("User " + hold.getUsername() + " has a balance of $" + hold.getBalance());
				break;
			default:
				System.out.println("No valid choice entered. Please try again");
			}
		}while(choice != 1);
	}

}
