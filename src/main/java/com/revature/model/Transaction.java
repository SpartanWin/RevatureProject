package com.revature.model;

import java.util.Date;

public class Transaction {
	
	private int id;
	private String username;
	private double amount;
	private Date timeDate;
	private String reciever;
	
	public Transaction() {
		
	}
	
	public Transaction(String un, double am, String rec) {
		this.username = un;
		this.amount = am;
		this.reciever = rec;
	}
	
	public Transaction(int i, String un, double am, String rec, Date td) {
		this.id = i;
		this.username = un;
		this.amount = am;
		this.reciever = rec;
		this.timeDate = td;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getReciever() {
		return reciever;
	}
	public void setReciever(String reciever) {
		this.reciever = reciever;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((reciever == null) ? 0 : reciever.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (id != other.id)
			return false;
		if (reciever == null) {
			if (other.reciever != null)
				return false;
		} else if (!reciever.equals(other.reciever))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", username=" + username + ", amount=" + amount + ", reciever=" + reciever
				+ "]";
	}
	
	
	

}
