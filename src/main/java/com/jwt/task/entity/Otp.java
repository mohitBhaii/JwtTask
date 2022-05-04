package com.jwt.task.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Otp {
	private int number;
	@Id
	private String email;
	private LocalDateTime date;
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Otp(int number, String email, LocalDateTime date) {
		super();
		this.number = number;
		this.email = email;
		this.date = date;
	}
	public Otp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
