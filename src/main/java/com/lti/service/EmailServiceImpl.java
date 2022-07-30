package com.lti.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.lti.dao.CustomerDao;
import com.lti.entity.Customer;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	MailSender mailSender;

	@Autowired
	CustomerDao customerDao;
	
	public void sendMailForOtp(String otp, Customer customer) {
		// TODO Auto-generated method stub
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("do-not-reply-mpsas@hotmail.com");
		message.setTo(customer.getEmailId());
		message.setSubject("Otp for transaction");
		message.setText("Greetings "+customer.getName()+", Your Otp is "+otp);
		mailSender.send(message);

	}

}
