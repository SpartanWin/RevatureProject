package com.revature.main;

import com.revature.ui.Login;
import com.revature.ui.MainMenu;
import com.revature.ui.Menu;

public class App {
	
	public static void main (String[] args) {
		
		Login menu = new MainMenu();
		menu.display();
		menu.sc.close();
		
	}

}
