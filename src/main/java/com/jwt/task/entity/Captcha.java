package com.jwt.task.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name ="captcha_table")
public class Captcha implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	private String captcha;
	@Id
	private int id;
	private LocalDateTime date;

	public String getCaptcha() {
		return captcha;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Captcha(String captcha, int id, LocalDateTime date) {
		super();
		this.captcha = captcha;
		this.id = id;
		this.date = date;
	}

	public Captcha() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static Captcha captchaGenerator() {
		Random random = new Random();
		String character = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String captcha= "";
		for(int i=0; i<2; i++) {
			captcha = captcha+character.charAt(random.nextInt(0,25));
			captcha = captcha+character.charAt(random.nextInt(25,61));
			captcha = captcha+character.charAt(random.nextInt(25,61));
		}		
		return  new Captcha(captcha, random.nextInt(100, 200), LocalDateTime.now());
	}

	@Override
	public String toString() {
		return "Captcha [captcha=" + captcha + ", id=" + id + "]";
	}

	
}
