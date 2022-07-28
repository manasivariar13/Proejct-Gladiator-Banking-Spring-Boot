package com.lti.service;

import java.util.List;

import com.lti.dto.AccountSummaryDto;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.ViewAllBeneficiariesDto;
import com.lti.entity.Account;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Transaction;

public interface CustomerService {

	String addBeneficiary(BeneficiaryAccountDto beneficiary);

	List<ViewAllBeneficiariesDto> viewAllBeneficiaries(int accNo);

	String deleteBeneficiary(int beneficiaryId);

	BeneficiaryAccountDto findBeneficiary(int beneficiaryId);

	List<TopFiveTransactionDto> findTopFiveTransactions(int accNo);

	String signup(Customer customer);

	boolean login(int customerId, String password);

//	Account services 

	String openAccount(Customer customer);

	AccountSummaryDto accountSummary(int accountNumber);

	List<TopFiveTransactionDto> accountStatement(int accountNumber);

	String fundTransfer(Account fromAccount, Account toAccount, double amount);

}
