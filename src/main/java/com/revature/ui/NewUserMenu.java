package com.revature.ui;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.exceptions.UserNotFoundException;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class NewUserMenu implements Login {
	
	private static Logger log = Logger.getLogger(UserMenu.class);
	public UserService userService = new UserService();
	Menu userMenu = new UserMenu();
	String fn = "";
	String ln = "";
	User hold = null;
	double startFunds = 0;
	boolean done = false;
	boolean unCheck = false;
	Connection con;
	
	public void display() {
		String usernameAttempt = "";
		String passwordAttempt = "";
		
		do {
			System.out.println("===NEW USER MENU===");
			//check that their chosen username isn't already in use
			while(usernameAttempt == "") {
			System.out.println("Please enter a unique username:");
			usernameAttempt = Menu.sc.nextLine();
			try {
				hold = userService.getUserByUsername(usernameAttempt);
			}catch(UserNotFoundException | SQLException e) {
				//no error message need here as it a check
			}
			if(hold != null)
			{
				hold = null;
				usernameAttempt = "";
				continue;
			}
			
			}
			
			//enter in your personal info
			while(fn == "") {
				System.out.println("Please enter your first name:");
				fn = Menu.sc.nextLine();
			}
			
			while(ln == "") {
				System.out.println("Please enter your last name:");
				ln = Menu.sc.nextLine();
			}
			
			while(startFunds <= 0) {
				System.out.println("Please enter your initial funds:");
				startFunds = Double.parseDouble(Menu.sc.nextLine());
				if(startFunds <= 0) {
					System.out.println("That is an invalid ammount");
					startFunds = 0;
				}
			}
			
			while(passwordAttempt == "") {
				System.out.println("Please enter your password:");
				passwordAttempt = Menu.sc.nextLine();
			}
			hold = new User(usernameAttempt,fn,ln,passwordAttempt,startFunds, false);
			try (Connection con = ConnectionUtil.getConnection()){
				userService.userDAO.insertApplicant(hold, con, usernameAttempt);
			}catch(SQLException e) {
				System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
			}
			log.info("Applicant " + hold.getUsername() + " has been added.");
			System.out.println("Thank you! We will check on your application shortly.");
			done = true;
			
			
		}while(done != true);
		
		//send them back to the main menu as all they can do is now done
		Login mainMenu = new MainMenu();
		mainMenu.display();
	}

}
