package com.jwt.task.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jwt.task.entity.Captcha;


@Repository
public interface CaptchaRepository extends JpaRepository<Captcha, Integer> {
	@Query(value="select * from captcha_table where id = :num", nativeQuery = true)
	public List<Captcha> getCaptcha(@Param("num") int id);

}
