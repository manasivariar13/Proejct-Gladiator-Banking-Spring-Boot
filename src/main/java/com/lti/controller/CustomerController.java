package com.lti.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AccountSummaryDto;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.dto.FundTransferDto;
import com.lti.dto.LoginDto;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.ViewAllBeneficiariesDto;
import com.lti.entity.Account;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Transaction;
import com.lti.entity.User;
import com.lti.service.CustomerService;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class CustomerController {

	@Autowired
	CustomerService customerService;

//	@RequestMapping(value = "/addBeneficiary", method = RequestMethod.POST)
	@PostMapping(value = "/addBeneficiary")
	public String addBeneficiary(@RequestBody BeneficiaryAccountDto beneficiary) {
		String message = customerService.addBeneficiary(beneficiary);
		return message;
	}

	@GetMapping(value = "/findBeneficiary/{beneficiaryId}")
	public BeneficiaryAccountDto findBeneficiaryById(@PathVariable int beneficiaryId) {
		return customerService.findBeneficiary(beneficiaryId);
	}

	@GetMapping(value = "/viewAllBeneficiaries/{accNo}")
	public List<ViewAllBeneficiariesDto> viewAllBeneficiaries(@PathVariable int accNo) {
		return customerService.viewAllBeneficiaries(accNo);
	}

	@DeleteMapping(value = "/deleteBeneficiary/{beneficiaryId}")
	public String deleteBeneficiary(@PathVariable int beneficiaryId) {
		String message = customerService.deleteBeneficiary(beneficiaryId);
		return message;
	}

	@PostMapping(value = "/openAccount")
	public String openAccount(@RequestBody Customer customer) {
//		return customer.getAadhaarNo();
		System.out.print("Hiiiiiiiiiii");
		System.out.print(customer.getEmailId());
//		return customer.get("emailId");
		return "Test";
//		return customerService.openAccount(customer);
	}

	@GetMapping(value = "/accountSummary/{accountNumber}")
	public AccountSummaryDto accountSummary(@PathVariable int accountNumber) {
		return customerService.accountSummary(accountNumber);
	}

	@GetMapping(value = "/findTopFiveTransactions/{accountNumber}")
	public List<TopFiveTransactionDto> findTopFiveTransactions(@PathVariable int accountNumber) {
		return customerService.findTopFiveTransactions(accountNumber);
	}

	@GetMapping(value = "/isCustomerExists/{accountNumber}")
	public boolean isCustomerExists(@PathVariable int accountNumber) {
		return customerService.isCustomerExists(accountNumber);
	}

	@GetMapping(value = "/accountStatement/{accountNumber}")
	public List<TopFiveTransactionDto> accountStatement(@PathVariable int accountNumber) {
		return customerService.accountStatement(accountNumber);
	}

	@PostMapping(value = "/fundTransfer")
	public String fundTransfer(@RequestBody FundTransferDto ftDto) {
		return customerService.fundTransfer(ftDto.getFromAccount(), ftDto.getToAccount(), ftDto.getAmount());
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@RequestBody User user) {
		return customerService.signup(user);
	}

	@PostMapping(value = "/login")
	public User login(@RequestBody LoginDto loginDto) {
		return customerService.login(loginDto.getUserId(), loginDto.getLoginPassword());
	}

}
