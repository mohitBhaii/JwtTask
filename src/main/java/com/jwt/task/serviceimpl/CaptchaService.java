package com.jwt.task.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.task.dao.CaptchaRepository;
import com.jwt.task.entity.Captcha;

@Service
public class CaptchaService {
	
	@Autowired
	private CaptchaRepository captchaRepository;
	public Captcha save(Captcha captcha){
		return captchaRepository.saveAndFlush(captcha);
		
	}
	public void deleteAll() {
		try {
		captchaRepository.deleteAll();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public boolean validateCaptcha(Captcha captcha) {
		System.out.println("In validate methoid");
		
		List<Captcha> dbCaptcha = captchaRepository.getCaptcha(captcha.getId());
		System.out.println(dbCaptcha);
		System.out.println("First captcha"+dbCaptcha.get(0));
		int a =captcha.getCaptcha().compareTo(dbCaptcha.get(0).getCaptcha());
		
		System.out.println("DB date "+dbCaptcha.get(0).getDate());
		System.out.println("Given Date "+ captcha.getDate());
		if(a==0) {
			int b = dbCaptcha.get(0).getDate().compareTo(captcha.getDate().minusMinutes(5));
			if(b>=0) {
			return true;
			}
			else {
				return false;
			}
		}else {
			return false;
			//throw new CaptchNotValidException("Captcha Not valid...Please Enter Right Captcha");
		}
	}
}
