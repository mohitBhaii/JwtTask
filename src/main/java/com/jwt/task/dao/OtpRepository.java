package com.jwt.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jwt.task.entity.Otp;


@Repository
public interface OtpRepository extends JpaRepository<Otp, String>{

	@Query(value="select * from otp where number = :num ", nativeQuery = true)
	Otp getByNumber(@Param("num") int number);
	
	

}
