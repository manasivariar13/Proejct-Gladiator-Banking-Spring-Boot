package com.lti.service;

import java.util.List;

import com.lti.dao.CustomerDao;
import com.lti.dto.AccountSummaryDto;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.ViewAllBeneficiariesDto;
import com.lti.dto.adminLoginD;
import com.lti.entity.Account;
import com.lti.entity.Admin;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Transaction;
import com.lti.entity.User;


public interface CustomerService {

	String addBeneficiary(BeneficiaryAccountDto beneficiary);

	List<ViewAllBeneficiariesDto> viewAllBeneficiaries(int accNo);

	String deleteBeneficiary(int beneficiaryId);

	BeneficiaryAccountDto findBeneficiary(int beneficiaryId);

	List<TopFiveTransactionDto> findTopFiveTransactions(int accNo);

	boolean isCustomerExists(int accountNumber);

	String signup(User user);

	User login(int userId, String password);

//	Account services 

	String openAccount(Customer customer);

	AccountSummaryDto accountSummary(int accountNumber);

	List<TopFiveTransactionDto> accountStatement(int accountNumber);

	String fundTransfer(Account fromAccount, Account toAccount, double amount);
	
	public adminLoginD readAdminInfo(String adminId);
	boolean adminLogin(String adminId, String adminPassword);
	
	List<Customer> pendingRequest();
	public void createAdmin(Admin admin);
	
//	public Admin addAdmin();
	
	String updatePendingRequests(int customerId, String response);
	
	
}
