package com.lti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.Status;
import com.lti.dto.Status.StatusCode;
import com.lti.exception.ServiceException;
import com.lti.service.OtpService;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class OtpController {

	@Autowired
	OtpService otpService;

	@GetMapping(path = "/generate-otp/{custId}")
	public Status getOtp(@PathVariable int custId) {
		try {
			boolean check = otpService.generateOtp(custId);
			Status status = new Status();
			if (check) {
				status.setStatusCode(StatusCode.SUCCESS);
				status.setStatusMessage("Otp Generated Successfully");
			} else {
				status.setStatusCode(StatusCode.FAILURE);
				status.setStatusMessage("Otp Generation Failed");
			}
			return status;
		} catch (ServiceException e) {
			Status status = new Status();
			status.setStatusCode(StatusCode.FAILURE);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	}
}
