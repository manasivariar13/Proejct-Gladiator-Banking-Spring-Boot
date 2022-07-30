package com.lti.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lti.dao.CustomerDao;
import com.lti.dao.OtpDao;
import com.lti.entity.Customer;
import com.lti.entity.Otp;
import com.lti.exception.ServiceException;

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	CustomerDao customerDao;
	
//	@Autowired
	
	@Autowired
	OtpGenerator otpGenerator;
	
	@Autowired
	OtpDao otpDao;
	
	@Autowired
	EmailService emailService;
	
	
	
	@Override
	public boolean generateOtp(int custId) {
		// TODO Auto-generated method stub
		if(customerDao.isCustExists(custId)) {
			Customer customer=customerDao.fetchById(Customer.class, custId);
			Otp otp=new Otp();
			otp.setOtp(otpGenerator.generateOtp());
			otp.setUserId(custId);
			otp.setDateTime(LocalDateTime.now());
			emailService.sendMailForOtp(otp.getOtp(), customer);
			return true;
		}else {
			throw new ServiceException("User does not exists");
		}
	}

	@Override
	public boolean checkOtp(int custId, String otp) {
		// TODO Auto-generated method stub
		try {
			if(customerDao.isCustExists(custId)) {
				return otp.equals(otpDao.getOtp(custId));
			}
			else {
				throw new ServiceException("User does not exists");
			}
		}
		catch(EmptyResultDataAccessException e) {
			throw new ServiceException("Invalid Otp");
		}
	}

}
