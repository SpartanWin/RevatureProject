package com.revature.ui;

public class MainMenu implements Login{

	public void display() {
		System.out.println("Welcome to the bank app");
		
		int choice = 0;
		
		do {
			System.out.println("===MAIN MENU===");
			System.out.println("Please select an option below:");
			System.out.println("1. Exit Application");
			System.out.println("2. New User");
			System.out.println("3. User Login");
			System.out.println("4. Employee Login");
			try{
				choice = Integer.parseInt(Menu.sc.nextLine());
			}catch(NumberFormatException e) {
				
			}
			
			//choose your option
			switch(choice) {
			case 1:
				break;
			case 2:
				Login newUserMenu = new NewUserMenu();
				newUserMenu.display();
				break;
			case 3:
				Login logMenu = new LoginMenu();
				logMenu.display();
				break;
			case 4:
				Login empMenu = new EmployeeLogin();
				empMenu.display();
				break;
			default:
				System.out.println("No valid choice entered. Please try again");
			}
		}while(choice != 1);
	}

}
