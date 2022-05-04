package com.jwt.task.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.task.service.JwtService;
import com.jwt.task.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtResquestFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
	 final	String header = request.getHeader("Authorization");
     String username = null;
	 String jwttoken = null;
	 if(header != null && header.startsWith("Bearer ")) {
		 jwttoken =header.substring(7);
		 
		 try {
			 
			username = jwtUtil.getUsernameFromToken(jwttoken);
			 
		 }catch (IllegalArgumentException e) {
	
		 System.out.println("unable to get token");
		 }catch (ExpiredJwtException e) {
		System.out.println("jwt token is expired");
		 }
	 }else {
		System.out.println("jwt token does not start with Bearer");
	}
	 
	 if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
		 UserDetails userdetails = jwtService.loadUserByUsername(username);
	 
		 if(jwtUtil.validateToken(jwttoken, userdetails)) {
			 
			 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userdetails,
					 null, userdetails.getAuthorities());
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		 
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		 }
	 
	 }
	 filterChain.doFilter(request, response);
	}

}
