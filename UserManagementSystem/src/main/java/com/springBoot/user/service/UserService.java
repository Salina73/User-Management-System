 package com.springBoot.user.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.springBoot.exception.Exception;
import com.springBoot.response.Response;
import com.springBoot.response.ResponseToken;

import com.springBoot.user.dto.Logindto;

import com.springBoot.user.dto.Userdto;

import com.springBoot.user.model.UserManager;
@Service
public interface UserService 
{
	//register
	Response register(Userdto userDto) throws Exception, UnsupportedEncodingException;

	//Login
	ResponseToken login(Logindto loginDto) throws Exception, UnsupportedEncodingException;

	List<UserManager> userList(String token);

	List<UserManager> latestRegisteredUsers(String token);

	int countOfUsers(String token);

	UserManager adminProfile(String token);

	



	
}