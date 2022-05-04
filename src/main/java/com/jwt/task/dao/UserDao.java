package com.jwt.task.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jwt.task.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {

	@Query(value ="select * from user where email like :email ", nativeQuery=true)
	List<User> findByEmail( @Param("email") String email);
}
