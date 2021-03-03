package com.revature.ui;

import java.sql.SQLException;

import com.revature.exceptions.UserNotFoundException;
import com.revature.model.User;
import com.revature.services.UserService;

public class EmployeeLogin implements Login{
	
	public UserService userService = new UserService();
	Menu employeeMenu = new EmployeeMenu();
	User hold = null;
	public void display() {
		String usernameAttempt = "";
		String passwordAttempt = "";
		
		do {
			System.out.println("===LOGIN MENU===");
			//make sure the username is on file
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
			
			//make sure they can't continue if they are not an employee
			if(hold.isEmployee() == false)
			{
				System.out.println("That is not an employee account.");
				hold = null;
				continue;
			}
			
			//make sure their passwords match up
			while(passwordAttempt == "") {
				System.out.println("Please enter your password:");
				passwordAttempt = Menu.sc.nextLine();
				if(passwordAttempt.equals(hold.getPassword()) != true) {
					passwordAttempt = "";
				}
			}
			
			employeeMenu.display(hold);
			
			
		}while(passwordAttempt != "");
	}

}
