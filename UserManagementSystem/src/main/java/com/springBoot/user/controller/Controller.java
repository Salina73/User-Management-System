package com.springBoot.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springBoot.exception.Exception;
import com.springBoot.response.Response;
import com.springBoot.response.ResponseToken;
import com.springBoot.user.dto.Logindto;

import com.springBoot.user.dto.Userdto;
import com.springBoot.user.model.UserManager;
import com.springBoot.user.service.UserService;

@CrossOrigin( origins = "*")

@RestController
@RequestMapping("/user")
public class Controller 
{
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody Userdto userDto)
			throws Exception, UnsupportedEncodingException 
	{
		Response response = userService.register(userDto);
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@RequestBody Logindto logindto)
			throws Exception, UnsupportedEncodingException
	{
		ResponseToken response = userService.login(logindto);
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getAllUsers")
	public List<UserManager> getAllUsers(@RequestHeader String token)
	{
		List<UserManager> list= userService.userList(token);
		return list;
	}
	@GetMapping("/latestRegistrations")
	public List<UserManager> latestRegistrations(@RequestHeader String token)
	{
		List<UserManager> list= userService.latestRegisteredUsers(token);
		return list;
	}
	@GetMapping("/totalRegistrations")
	public int totalRegistrations(@RequestHeader String token)
	{
		int list= userService.countOfUsers(token);
		return list;
	}
	@GetMapping("/profileOfAdmin")
	public UserManager adminProfile(@RequestHeader String token)
	{
		UserManager user= userService.adminProfile(token);
		return user;
	}
}
