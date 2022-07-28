package com.lti.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dao.CustomerDao;
import com.lti.dto.AccountSummaryDto;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.ViewAllBeneficiariesDto;
import com.lti.entity.Account;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Transaction;
import com.lti.exception.InsufficientFundsException;
import com.lti.exception.MinimumAmountException;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDao dao;

	public String addBeneficiary(BeneficiaryAccountDto beneficiary) {
		Beneficiary ben = new Beneficiary();
		ben.setBeneficiaryId(beneficiary.getBeneficiaryId());
		ben.setCustomerAccountNumber(beneficiary.getCustomerAccountNumber());
		ben.setBeneficiaryName(beneficiary.getBeneficiaryName());
		Account acc = new Account();
		acc.setAccountNumber(beneficiary.getBeneficiaryAccount());
		ben.setBeneficiaryAccount(acc);
//		System.out.print(beneficiary.getBeneficiaryAccount().getAccountNumber());
		try {
			if (dao.isCustomerExists(ben.getBeneficiaryAccount().getAccountNumber())) {
				Beneficiary beneficiary2 = dao.addBeneficiary(ben);
				return "Beneficiary added successfully. Beneficiary ID : " + beneficiary2.getBeneficiaryId();
			} else {
				return "Beneficiary doesn't exists. Please add correct account number.";
			}
		} catch (Exception e) {
			return e.getMessage();
//			return "Unexpected Error occured. Beneficiary addition failed.";
		}
	}

	public List<ViewAllBeneficiariesDto> viewAllBeneficiaries(int accNo) {
		List<ViewAllBeneficiariesDto> viewBen = new ArrayList<>();
		List<Beneficiary> ben = new ArrayList<>();
		ben = dao.viewAllBeneficiaries(accNo);
		for (Beneficiary beneficiary : ben) {
			ViewAllBeneficiariesDto viewAll = new ViewAllBeneficiariesDto();
			viewAll.setAccountNumber(beneficiary.getBeneficiaryAccount().getAccountNumber());
			viewAll.setBeneficiaryId(beneficiary.getBeneficiaryId());
			viewAll.setBeneficiaryName(beneficiary.getBeneficiaryName());

			viewBen.add(viewAll);
		}

		return viewBen;
	}

	public String deleteBeneficiary(int beneficiaryId) {
		try {
			dao.deleteBeneficiary(beneficiaryId);
			return "Beneficiary deleted successfully.";
		} catch (Exception e) {
			return "Unexpected Error occured. Beneficiary was not deleted.";
		}

	}

	public BeneficiaryAccountDto findBeneficiary(int beneficiaryId) {
		Beneficiary ben = new Beneficiary();
		ben = dao.findBeneficiaryById(beneficiaryId);
		BeneficiaryAccountDto dto = new BeneficiaryAccountDto();
		dto.setBeneficiaryAccount(ben.getBeneficiaryAccount().getAccountNumber());
		dto.setBeneficiaryId(ben.getBeneficiaryId());
		dto.setBeneficiaryName(ben.getBeneficiaryName());
		dto.setCustomerAccountNumber(ben.getCustomerAccountNumber());
//		dto.setBalance(ben.getBeneficiaryAccount().getBalance());

		return dto;
	}

//	Account service implementations

	public String openAccount(Customer customer) {
//		System.out.print(customer.getEmailId());
		try {
//			Account acc = dao.openAccount(account);
			return "Registration has been initiated. We'll let you know the Account status soon. Your customer ID: "
					+ customer.getCustId();
		} catch (Exception e) {
			return e.getMessage();
//			return "Unexpected error occured. Account Registration failed.";
		}
	}

	public List<TopFiveTransactionDto> findTopFiveTransactions(int accountNumber) {
		List<TopFiveTransactionDto> topTrans = new ArrayList<>();
		List<Transaction> transactions = new ArrayList<>();
		transactions = dao.findtoprec(accountNumber);

		for (Transaction t : transactions) {
			TopFiveTransactionDto top = new TopFiveTransactionDto();
			top.setTransactionId(t.getTransactionId());
			top.setAccountNumber(t.getAccount().getAccountNumber());
			top.setBalance(t.getAmount());
			top.setTransactionDate(t.getTransactionDate());
			top.setTransactionMode(t.getMode());
			top.setTransactionType(t.getTransactionType());

			topTrans.add(top);
		}
		return topTrans;

	}

	public AccountSummaryDto accountSummary(int accountNumber) {
		AccountSummaryDto accSummDto = new AccountSummaryDto();
		Account acc = new Account();
		acc = dao.accountSummary(accountNumber);

		accSummDto.setAccountNumber(acc.getAccountNumber());
		accSummDto.setBalance(acc.getBalance());

		return accSummDto;
	}

	public List<TopFiveTransactionDto> accountStatement(int accountNumber) {
		List<TopFiveTransactionDto> topTrans = new ArrayList<>();
		List<Transaction> transactions = new ArrayList<>();
		transactions = dao.accountStatement(accountNumber);

		for (Transaction t : transactions) {
			TopFiveTransactionDto top = new TopFiveTransactionDto();
			top.setTransactionId(t.getTransactionId());
			top.setAccountNumber(t.getAccount().getAccountNumber());
			top.setBalance(t.getAmount());
			top.setTransactionDate(t.getTransactionDate());
			top.setTransactionMode(t.getMode());
			top.setTransactionType(t.getTransactionType());

			topTrans.add(top);
		}
		return topTrans;
	}

	public String fundTransfer(Account fromAccount, Account toAccount, double amount) {
		try {
			if (amount < 100) {
				throw new MinimumAmountException("Amount must be greater than 100");
//				return "Amount must be minimum 100";
			} else if (amount > fromAccount.getBalance()) {
				throw new InsufficientFundsException("Insufficent balance");
//				return "Insufficent balance";
			} else {
				Transaction transaction = dao.fundTransfer(fromAccount, toAccount, amount);
				return "Fund transfer successful. Transaction ID: " + transaction.getTransactionId();
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String signup(Customer customer) {
		try {
			Customer customer2 = dao.addOrUpdateCustomer(customer);
			return "Sign up successful. Your userId is: " + customer2.getCustId();
		} catch (Exception e) {
			return "Unexpected error occured. Sign up failed.";
		}
	}

	public boolean login(int customerId, String password) {
		return dao.login(customerId, password);
	}

}
