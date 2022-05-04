package com.jwt.task.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.task.dao.OtpRepository;
import com.jwt.task.entity.Mail;
import com.jwt.task.entity.Otp;
import com.jwt.task.service.MailService;
import com.jwt.task.service.OtpService;

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpRepository otpRepository;
	@Autowired
	private MailService mailService;

	@Override
	public void saveOtp(Otp otp) {
		try {
		otpRepository.deleteById(otp.getEmail());;
		}
		catch(Exception e) {
			
		}
		otp.setDate(LocalDateTime.now());
		otpRepository.save(otp);
			
		
		Mail mail = new Mail();
		mail.setMailFrom("malviyag4uu@gmail.com");
		mail.setMailTo(otp.getEmail());
		mail.setMailSubject("Reset Password Otp");
		mail.setMailContent("Hello " + otp.getEmail()+".....\nYour 4 digit Otp for reset password is :- "+otp.getNumber()); 
		mailService.sendEmail(mail);
	}

	@Override
	public Otp getOtp(String email) {
		
		return otpRepository.getById(email);
	}

	@Override
	public Otp getOtp(int otp) {
		
		return otpRepository.getByNumber(otp);
	}

	@Override
	public void deleteOtp(Otp otpobj) {
		try {
		otpRepository.delete(otpobj);
		otpRepository.deleteById(otpobj.getEmail());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
