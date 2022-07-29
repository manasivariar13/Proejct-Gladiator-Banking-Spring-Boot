package com.lti.service;

import java.util.List;

import com.lti.dto.AccountSummaryDto;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.dto.CustomerDto;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.ViewAllBeneficiariesDto;
import com.lti.entity.Account;
import com.lti.entity.Address;
import com.lti.entity.Admin;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Income;
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

	int openAccount(CustomerDto customerDto);

	AccountSummaryDto accountSummary(int accountNumber);

	List<TopFiveTransactionDto> accountStatement(int accountNumber);

	String fundTransfer(Account fromAccount, Account toAccount, double amount);

	boolean adminLogin(int adminId, String adminPassword);

	List<Customer> pendingRequest();

	public Admin addAdmin();

	String updatePendingRequests(int customerId, String response);

}
