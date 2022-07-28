package com.lti.dto;

import com.lti.entity.Account;

public class BeneficiaryAccountDto {
	private int beneficiaryId;
	private String beneficiaryName;
	private int customerAccountNumber;
	private int beneficiaryAccount;
//	private double balance;
//
//	public double getBalance() {
//		return balance;
//	}
//
//	public void setBalance(double balance) {
//		this.balance = balance;
//	}

	public int getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public int getCustomerAccountNumber() {
		return customerAccountNumber;
	}

	public void setCustomerAccountNumber(int customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}

	public int getBeneficiaryAccount() {
		return beneficiaryAccount;
	}

	public void setBeneficiaryAccount(int beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}

}
