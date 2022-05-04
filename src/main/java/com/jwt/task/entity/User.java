package com.jwt.task.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@Entity
public class User {

	@Id
	private String userName;
	private String userFirstName;
	private String userLastName;
	private String userPassword;
	@Column(unique = true)
	private String email;
	@Transient
	private Captcha captcha;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String userName, String userFirstName, String userLastName, String userPassword, String email,
			Captcha captcha, Set<Role> role) {
		super();
		this.userName = userName;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
		this.email = email;
		this.captcha = captcha;
		this.role = role;
	}
	public Captcha getCaptcha() {
		return captcha;
	}
	public void setCaptcha(Captcha captcha) {
		this.captcha = captcha;
	}
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name= "USER_ROLE", joinColumns = {
			@JoinColumn(name= "USER_ID")
	},
	    inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
	private Set<Role> role;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
