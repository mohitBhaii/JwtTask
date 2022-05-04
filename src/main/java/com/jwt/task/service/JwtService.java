package com.jwt.task.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.task.dao.UserDao;
import com.jwt.task.entity.JwtRequest;
import com.jwt.task.entity.JwtResponse;
import com.jwt.task.entity.User;
import com.jwt.task.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		atuhenticate(userName, userPassword);
		
		final UserDetails userDetails = loadUserByUsername(userName);
		
		String generateToken = jwtUtil.generateToken(userDetails);
		
		User user = userDao.findById(userName).get();
		
		return new JwtResponse(user, generateToken);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userDao.findById(username).get();
		if(user !=null) {
			return new org.springframework.security.core.userdetails.User(user.getUserName(),
					user.getUserPassword(),
					getAuthoraties(user));
		}else {
			throw new UsernameNotFoundException("User is not valid");
		}
	}
	
	public Set<SimpleGrantedAuthority> getAuthoraties(User user) {
		Set<SimpleGrantedAuthority> authoraties = new HashSet<>();
		
		user.getRole().forEach(role -> {
			authoraties.add( new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		});
		
		return authoraties;
	}
	
	public void atuhenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
					
		}catch(DisabledException e) {
			throw new Exception("user is disabled");
		}catch(BadCredentialsException a) {
			throw new Exception("bad credentials from user");
		}
		
	}

}
