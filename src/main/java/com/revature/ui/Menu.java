package com.revature.ui;

import java.util.Scanner;

import com.revature.model.User;

public interface Menu {
	
	public static final Scanner sc = new Scanner(System.in);
	
	void display(User user);

}
