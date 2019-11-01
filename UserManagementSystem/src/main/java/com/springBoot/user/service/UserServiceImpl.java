package com.springBoot.user.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.tools.JavaFileManager.Location;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.springBoot.exception.Exception;
import com.springBoot.response.Response;
import com.springBoot.response.ResponseToken;

import com.springBoot.user.dto.Logindto;

import com.springBoot.user.dto.Userdto;
import com.springBoot.user.model.LoginHistory;
import com.springBoot.user.model.UserManager;
import com.springBoot.user.repository.LoginRepo;
import com.springBoot.user.repository.UserRepo;

import com.springBoot.utility.ResponseHelper;
import com.springBoot.utility.TokenGeneration;
import com.springBoot.utility.Utility;
import org.springframework.util.StringUtils;

@Component
@SuppressWarnings("unused")
@PropertySource("classpath:message.properties")
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private LoginRepo loginRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TokenGeneration tokenUtil;

	@Autowired
	TokenGeneration token1;

	@Autowired
	private Response statusResponse;
	@Autowired
	private Utility utility;

	@Autowired
	private Environment environment;

	public Response register(Userdto userDto) {
		UserManager user = modelMapper.map(userDto, UserManager.class);
		Optional<UserManager> alreadyPresent = userRepo.findByEmailId(user.getEmailId());
		if (alreadyPresent.isPresent()) {
			throw new Exception(environment.getProperty("status.register.emailExistError"));
		}
		user = userRepo.save(user);

		statusResponse = ResponseHelper.statusResponse(200, "status.register.success");
		return statusResponse;
	}

	public ResponseToken login(Logindto loginDto) {

		Optional<UserManager> user = userRepo.findByEmailId(loginDto.getEmailId());
		LoginHistory history=null;
		ResponseToken response = new ResponseToken();
		if (user.isPresent()) {
			String token = tokenUtil.createToken(user.get().getUserId());
			System.out.println("password..." + (loginDto.getPassword()));
			Long id=tokenUtil.decodeToken(token);
			history.setUserId(id);
			history.setLocalDateTime(LocalDateTime.now());
			loginRepo.save(history);
			response.setStatusCode(200);
			response.setToken(token);
			response.setStatusMessage(environment.getProperty("user.login"));
			return response;
		} else {
			response.setStatusCode(404);
			response.setStatusMessage(environment.getProperty("user.validation.email"));
			return response;
		}
	}

	@Override
	public List<UserManager> userList(String token) {
		Long id=tokenUtil.decodeToken(token);
		Optional<UserManager> user=userRepo.findById(id);
		if(user.get().getRole().equals("admin"))
		{
			return userRepo.findAll();
		}
		return null;
	}

	@Override
	public List<UserManager> latestRegisteredUsers(String token) {
		Long id=tokenUtil.decodeToken(token);
		Optional<UserManager> user=userRepo.findById(id);
		if(user.get().getRole().equals("admin"))
		{
			return userRepo.findAll(Sort.by("userId").descending());
		}
		return null;
	}

	@Override
	public int countOfUsers(String token) {
		Long id=tokenUtil.decodeToken(token);
		Optional<UserManager> user=userRepo.findById(id);
		if(user.get().getRole().equals("admin"))
		{
			return userRepo.findAll().size();
		}
		return 0;
	}

	@Override
	public UserManager adminProfile(String token) {
		Long id=tokenUtil.decodeToken(token);
		Optional<UserManager> user=userRepo.findById(id);
		if(user.get().getRole().equals("admin"))
		{
			return user.get();
		}
		return null;
	}

}