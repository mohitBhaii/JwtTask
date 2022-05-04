package com.jwt.task.controller;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.task.dao.UserDao;
import com.jwt.task.entity.Captcha;
import com.jwt.task.entity.Otp;
import com.jwt.task.entity.User;
import com.jwt.task.service.OtpService;
import com.jwt.task.service.UserService;
import com.jwt.task.serviceimpl.CaptchaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserController {

	@Autowired
	private UserService userserv;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private OtpService otpService;
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CaptchaService captchaService;

	Random random = new Random();

	@PostConstruct
	public void initRolesandUser() {
		userserv.initRolesandUser();
	}

	@PostMapping("/adduser")
	public User registerNewUser(@RequestBody User user) {
		return userserv.registrNewUser(user);
	}

	@GetMapping("/foradmin")
	@PreAuthorize("hasRole('admin')")
	public String forAdmin() {
		return "this page is for admin accessible";
	}

	@GetMapping("/foruser")

	@PreAuthorize("hasRole('user')")
	// @PreAuthorize("hasAnyRole('user','admin')")
	public String foruser() {
		return "this url is for user accessible";
	}

	public static void main(String[] args) {
		String res = new UserService().getEncodedPassword("password");
		System.out.println(res);
	}

	@GetMapping("/user/{userName}")
	public User getUser(@PathVariable("userName") String userName) {
		User user = userDao.findById(userName).get();
		user.setUserPassword(new String(Base64.getDecoder().decode(user.getUserPassword().getBytes())));
		return user;
	}

	@PostMapping("/sendotp")
	public String getOtp(@RequestParam("email") String email) {
		List<User> users = userDao.findByEmail(email);
		User user = users.get(0);
		if (user == null) {
			return "User Not Found with this email";
		} else {
			Integer number = random.nextInt(1000, 9999);
			Otp otp = new Otp();
			otp.setEmail(email);
			otp.setNumber(number);

			otpService.saveOtp(otp);
			return "Otp SuccessFully send to Your email : " + otp.getEmail() + " and the otp is :-" + otp.getNumber();
		}
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam("otp") int otp, @RequestParam("password") String password,
			@RequestParam("again_password") String againPassword) {
		if (!password.equals(againPassword)) {
			return "Password Does Not match";
		}

		Otp otpobj = otpService.getOtp(otp);

		if (otpobj == null) {
			return "otp Mismatch!!!! try again";
		} else {
			if (otpobj.getDate().compareTo(LocalDateTime.now().minusMinutes(2)) >= 0) {
				String email = otpobj.getEmail();
				List<User> users = userDao.findByEmail(email);
				User user = users.get(0);
				user.setUserPassword(passwordEncoder.encode(againPassword));
				userDao.save(user);
				otpService.deleteOtp(otpobj);
				return "Password SuccessFully Changed";
			} else {
				return "OTP Time expire";
			}
		}
		
	}
	
	@GetMapping("/getcaptcha")
	public Captcha getCaptcha() {
		Captcha captcha = Captcha.captchaGenerator();
		captchaService.deleteAll();
		return captchaService.save(captcha);
}
}