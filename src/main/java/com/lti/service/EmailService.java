package com.lti.service;

import com.lti.entity.Customer;

public interface EmailService {
	
	void sendMailForOtp(String otp, Customer customer);

}
