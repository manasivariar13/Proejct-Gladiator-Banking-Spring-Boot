package com.lti.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OtpGeneratorImpl implements OtpGenerator {

	@Override
	public String generateOtp() {
		// TODO Auto-generated method stub
		Random random=new Random();
		return String.format("%04d", random.nextInt(10000));
	}

}
