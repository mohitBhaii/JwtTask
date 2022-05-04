package com.jwt.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.task.entity.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {

}
