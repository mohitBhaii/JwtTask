package com.jwt.task.service;

import org.springframework.stereotype.Service;

import com.jwt.task.entity.Otp;
@Service
public interface OtpService {

	void saveOtp(Otp otp);
	Otp getOtp(String email);
	Otp getOtp(int otp);
	void deleteOtp(Otp otpobj);
}
