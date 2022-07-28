package com.lti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AdminLoginDto;
import com.lti.dto.BeneficiaryAccountDto;
import com.lti.entity.Admin;
import com.lti.entity.Customer;
import com.lti.service.CustomerService;

@RestController
@RequestMapping("/admin")
@CrossOrigin()
public class AdminController {
	@Autowired
	CustomerService customerService;
	
	@PostMapping(value = "/adminLogin")
	public boolean adminLogin(@RequestBody AdminLoginDto adminDto) {
		return customerService.adminLogin(adminDto.getAdminId(), adminDto.getAdminPassword());
	}
	
	@GetMapping(value = "/pendingRequests")
	public List<Customer> getPendingRequests(){
		return customerService.pendingRequest();
	}
	
	@PutMapping(value = "/updateRequest")
	public void updateRequest(@RequestBody int customerId, String response) {
		customerService.updatePendingRequests(customerId, response);
	}
	
	@PostMapping(value = "/createAdmin")
	public Admin addAdmin() {
		return customerService.addAdmin();
	}

}
