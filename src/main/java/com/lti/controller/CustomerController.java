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
import com.lti.dto.CustomerDto;
import com.lti.dto.FundTransferDto;
import com.lti.dto.LoginDto;
import com.lti.dto.OpenAccountDto;
import com.lti.dto.SendAllBeneficiariesDto;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusCode;
import com.lti.dto.TopFiveTransactionDto;
import com.lti.dto.UserLoginStatus;
import com.lti.dto.ViewAllBeneficiariesDto;
import com.lti.entity.Account;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Transaction;
import com.lti.entity.User;
import com.lti.exception.ServiceException;
import com.lti.service.CustomerService;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class CustomerController {

	@Autowired
	CustomerService customerService;

//	@RequestMapping(value = "/addBeneficiary", method = RequestMethod.POST)
	@PostMapping(value = "/addBeneficiary")
	public Status addBeneficiary(@RequestBody BeneficiaryAccountDto beneficiary) {
		try {
			customerService.addBeneficiary(beneficiary);
			Status status = new Status();
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage("Beneficiary Added Successfully");
			return status;

		} catch (ServiceException e) {

			Status status = new Status();
			status.setStatusMessage(e.getMessage());
			status.setStatusCode(StatusCode.FAILURE);
			return status;
		}
	}

	@GetMapping(value = "/findBeneficiary/{beneficiaryId}")
	public BeneficiaryAccountDto findBeneficiaryById(@PathVariable int beneficiaryId) {
		return customerService.findBeneficiary(beneficiaryId);
	}

	@GetMapping(value = "/viewAllBeneficiaries/{accNo}")
	public SendAllBeneficiariesDto viewAllBeneficiaries(@PathVariable int accNo) {
//		SendAllBeneficiariesDto dto = new SendAllBeneficiariesDto();
//		dto.setBeneficiaryDto(customerService.viewAllBeneficiaries(accNo));
//
//		return dto;

		try {
			List<ViewAllBeneficiariesDto> list = customerService.viewAllBeneficiaries(accNo);
			SendAllBeneficiariesDto status = new SendAllBeneficiariesDto();
			status.setBeneficiaryDto(list);
			status.setStatusCode(StatusCode.SUCCESS);
			status.setStatusMessage("Beneficiary Fetched Successfully");
			return status;
		} catch (ServiceException e) {
			SendAllBeneficiariesDto status = new SendAllBeneficiariesDto();
			status.setBeneficiaryDto(null);
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	}

	@DeleteMapping(value = "/deleteBeneficiary/{beneficiaryId}")
	public String deleteBeneficiary(@PathVariable int beneficiaryId) {
		String message = customerService.deleteBeneficiary(beneficiaryId);
		return message;
	}

	@PostMapping(value = "/openAccount")
	public OpenAccountDto openAccount(@RequestBody CustomerDto customerDto) {
		try {
			int custId = customerService.openAccount(customerDto);
			OpenAccountDto dto = new OpenAccountDto();
			dto.setStatusCode(StatusCode.SUCCESS);
			dto.setStatusMessage("Registration Successful. Your customer ID is" + custId);
			dto.setCustId(custId);
			return dto;

		} catch (Exception e) {
			e.printStackTrace();
			OpenAccountDto dto = new OpenAccountDto();
			dto.setStatusCode(StatusCode.FAILURE);
			dto.setStatusMessage(e.getMessage());
			return dto;
		}
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
	public UserLoginStatus login(@RequestBody LoginDto loginDto) {
//		return customerService.login(loginDto.getUserId(), loginDto.getLoginPassword());
		try {
			User user = customerService.login(loginDto.getUserId(), loginDto.getLoginPassword());
			UserLoginStatus userLoginStatus = new UserLoginStatus();
			userLoginStatus.setStatusCode(StatusCode.SUCCESS);
			userLoginStatus.setStatusMessage("Login Successful");
			userLoginStatus.setUserId(user.getUserId());
			userLoginStatus.setAccountNumber(String.valueOf(user.getAccountNumber()));
			return userLoginStatus;
		} catch (ServiceException e) {
			UserLoginStatus userLoginStatus = new UserLoginStatus();
			userLoginStatus.setStatusCode(StatusCode.FAILURE);
			userLoginStatus.setStatusMessage(e.getMessage());
			return userLoginStatus;
		}
	}

}
