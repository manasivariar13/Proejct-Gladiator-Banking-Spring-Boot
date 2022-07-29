package com.lti.dao;

import java.util.List;

import com.lti.entity.Account;
import com.lti.entity.Admin;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Transaction;
import com.lti.entity.User;

public interface CustomerDao {

	Customer openAccount(Customer customer);

	Account accountSummary(int accountNumber);

	List<Transaction> accountStatement(int accountNumber);

	Transaction fundTransfer(Account fromAccount, Account toAccount, double amount);

//	Transaction addOrUpdateTransaction(Transaction transaction);

	List<Transaction> findtoprec(int accNo);

	boolean isCustomerExists(int accountNumber);

	Customer addOrUpdateCustomer(Customer customer);

	User login(int userId, String password);

	String signup(User user);

	Beneficiary addBeneficiary(Beneficiary beneficiary);

	List<Beneficiary> viewAllBeneficiaries(int accNo);

	Beneficiary findBeneficiaryById(int beneficiaryId);

	void deleteBeneficiary(int beneficiaryId);

//	boolean adminExists(int adminId);

	public Admin addAdmin();

	boolean adminLogin(int adminId, String adminPassword);

	List<Customer> pendingRequest();

	String updatePendingRequest(int customerId, String response);

}
