package com.lti.dto;

public class UserLoginStatus extends Status{
	
	private int userId;
	private String accountNumber;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String userName) {
		this.accountNumber = userName;
	}
	
	

}