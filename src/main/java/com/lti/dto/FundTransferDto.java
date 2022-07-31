package com.lti.dto;

import com.lti.entity.Account;
import com.lti.entity.TransactionType;

public class FundTransferDto {
	private String fromAccount;
	private String toAccount;
	private Double amount;
	private TransactionType transactionType;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TransactionType getType() {
		return transactionType;
	}

	public void setType(TransactionType type) {
		this.transactionType = type;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	
}
