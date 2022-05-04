package com.jwt.task.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.task.dao.RoleDao;
import com.jwt.task.dao.UserDao;
import com.jwt.task.entity.Role;
import com.jwt.task.entity.User;

@Service
public class UserService {

	@Autowired
	private UserDao userdao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@Autowired
	private RoleDao roledao;
	
	public User registrNewUser(User user) {
		Role role = roledao.findById("User").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
		
		return userdao.save(user);
	}
	
	public void initRolesandUser() {
		
		Role adminrole = new Role();
		
		adminrole.setRoleName("admin");
		adminrole.setRoleDescription("admin role");
		roledao.save(adminrole);
		
        Role userrole = new Role();
		
		userrole.setRoleName("user");
		userrole.setRoleDescription("default role for newly created user");
		roledao.save(userrole);
		
		
		User adminuser = new User();
		adminuser.setUserFirstName("admin");
		adminuser.setUserLastName("admin");
		adminuser.setUserName("admin");
		adminuser.setEmail("admin456@gmail.com");
		adminuser.setUserPassword(getEncodedPassword("password"));
		
		Set<Role> adminroles = new HashSet<>();
		adminroles.add(adminrole);
		adminuser.setRole(adminroles);
		userdao.save(adminuser); 
		
	/*	User user = new User();
		user.setUserFirstName("raj");
	    user.setUserLastName("soni");
		user.setUserName("userraj");
		user.setPassword(getEncodedPassword("raj@pass"));
		
		Set<Role> userroles = new HashSet<>();
		userroles.add(userrole);
		user.setRole(userroles);
		userdao.save(user); */
	}
	
	public String getEncodedPassword(String pass) {
		return passwordEncoder.encode(pass);
		
		
	}
	
}
