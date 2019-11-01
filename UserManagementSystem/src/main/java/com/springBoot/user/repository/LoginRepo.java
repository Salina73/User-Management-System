package com.springBoot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.user.model.LoginHistory;

@Repository
public interface LoginRepo extends JpaRepository<LoginHistory, Long> 
{
	
}
