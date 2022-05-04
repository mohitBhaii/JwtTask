package com.jwt.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.task.entity.Role;
import com.jwt.task.service.RoleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController

public class RoleController {

	@Autowired
	private RoleService rolservice;
	
	@PostMapping("/newrole")
	public Role createNewRole(@RequestBody Role role) {
		return rolservice.createNewrole(role);
		
	}
}
