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

import com.lti.dto.LoginDto;
import com.lti.dto.UserLoginStatus;
import com.lti.dto.adminLoginD;
import com.lti.dto.Status.StatusCode;
import com.lti.entity.Admin;
import com.lti.entity.Customer;
import com.lti.entity.User;
import com.lti.exception.ServiceException;
import com.lti.service.CustomerService;

@RestController
@RequestMapping("/admin")
@CrossOrigin()
public class AdminController {
	@Autowired
	CustomerService customerService;
//	AdminLoginDto AdminLoginDto;
	@PostMapping(value = "/adminLogin")
	public boolean adminLogin(@RequestBody AdminLoginDto adminDto) {
		return customerService.adminLogin(adminDto.getAdminId(), adminDto.getAdminPassword());
	}
	
//	@PostMapping(value = "/adminLogin")
//	public UserLoginStatus adminLogin(@RequestBody AdminLoginDto adminDto) {
//		return customerService.login(loginDto.getUserId(), loginDto.getLoginPassword());
		
		//		try {
//			Admin user = customerService.adminLogin(AdminLoginDto.getAdminId(), AdminLoginDto.getAdminPassword());
//			UserLoginStatus userLoginStatus = new UserLoginStatus();
//			userLoginStatus.setStatusCode(StatusCode.SUCCESS);
//			userLoginStatus.setStatusMessage("Login Successful");
////			userLoginStatus.setAdminId(user.getAdminId());
////			userLoginStatus.setAccountNumber(String.valueOf(user.getAccountNumber()));
//			return userLoginStatus;
//		}
//		catch (ServiceException e) {
//			UserLoginStatus userLoginStatus = new UserLoginStatus();
//			userLoginStatus.setStatusCode(StatusCode.FAILURE);
//			userLoginStatus.setStatusMessage(e.getMessage());
//			return userLoginStatus;
//		}
//	}
	
	@GetMapping(value = "/pendingRequests")
	public List<Customer> getPendingRequests(){
		return customerService.pendingRequest();
	}
	
	@PutMapping(value = "/updateRequest")
	public String updateRequest(@RequestBody int customerId, String response) {
		return customerService.updatePendingRequests(customerId, response);
	}
	
	//http://localhost:9090/admin/createadmin
	@PostMapping(path="/createadmin")
	public void createAdmin(@RequestBody Admin admin)
	{
		customerService.createAdmin(admin);
	}
	
	@PostMapping(path="/adminid")
	public adminLoginD getAdmin(@RequestBody String adminId)
	{
		 return customerService.readAdminInfo(adminId);
		 
	}
//	@PostMapping(value = "/createAdmin")
//	public Admin addAdmin() {
//		return customerService.addAdmin();
//	}

}
