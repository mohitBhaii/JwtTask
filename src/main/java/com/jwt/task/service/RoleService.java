package com.jwt.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.task.dao.RoleDao;
import com.jwt.task.entity.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao dao;
	public Role createNewrole(Role role) {
		return dao.save(role);
	}
}
