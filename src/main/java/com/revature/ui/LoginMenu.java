package com.revature.ui;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.exceptions.UserNotFoundException;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class LoginMenu implements Login{
	
	public UserService userService = new UserService();
	Menu userMenu = new UserMenu();
	User hold = null;
	public void display() {
		String usernameAttempt = "";
		String passwordAttempt = "";
		
		do {
			System.out.println("===LOGIN MENU===");
			while(hold == null) {
			System.out.println("Please enter your username:");
			usernameAttempt = Menu.sc.nextLine();
			try {
				hold = userService.getUserByUsername(usernameAttempt);
			}catch(UserNotFoundException | SQLException e) {
				System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
				hold = null;
			} 
			}
			
			while(passwordAttempt == "") {
				System.out.println("Please enter your password:");
				passwordAttempt = Menu.sc.nextLine();
				if(passwordAttempt.equals(hold.getPassword()) != true) {
					passwordAttempt = "";
				}
			}
			
			userMenu.display(hold);
			
			
		}while(passwordAttempt != "");
	}

}
