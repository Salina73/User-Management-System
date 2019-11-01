package com.springBoot.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.user.model.UserManager;

@Repository
public interface UserRepo extends JpaRepository<UserManager, Long> 
{
	public Optional<UserManager> findByEmailId(String emailId);
	
}
