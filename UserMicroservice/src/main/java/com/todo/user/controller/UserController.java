package com.todo.user.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.user.dto.ForgotPasswordDto;
import com.todo.user.dto.LoginDto;
import com.todo.user.dto.RegistrationDto;
import com.todo.user.dto.ResponseDto;
import com.todo.user.model.User;
import com.todo.user.security.JwtTokens;
//import com.todo.user.service.ProxyUserService;
import com.todo.user.service.RedisRepository;
import com.todo.user.service.UserService;
import com.todo.user.utility.Messages;
import com.todo.user.utility.Utility;
import com.todo.user.utility.exceptions.LoginExceptionHandling;
import com.todo.user.utility.exceptions.ToDoException;
import com.todo.user.utility.exceptions.UserExceptionHandling;

/*************************************************************************************************************
 *
 * purpose:Controller class for register and sign up using MongoDB
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	@Autowired
	RedisRepository redisrepo;
	@Autowired
	Messages messages;
	Utility utility = new Utility();
	@Autowired
	JwtTokens tokens;

	/**
	 * purpose:method to login
	 * 
	 * @param checkUser
	 * @return response
	 * @throws LoginExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException
	 * @throws UserExceptionHandling
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseDto login(@RequestBody LoginDto login, HttpServletResponse response)
			throws LoginExceptionHandling, ToDoException, UserExceptionHandling {
		String token;
		try {
			token = userService.login(login.getEmailId(), login.getPassword());
			response.setHeader("token", token);
			ResponseDto response1 = new ResponseDto();
			response1.setMessage(messages.get("251"));
			response1.setStatus(200);
			return response1;

		} catch (MessagingException e) {
			ResponseDto response1 = new ResponseDto();
			response1.setMessage("incorrectdetails");
			response1.setStatus(400);
			return response1;
		}
		// logger.info("Logging User : {}", checkUser);

		// Claims claims = tokens.parseJwt(token);
		// String userId = claims.getSubject();
		// redisrepo.setUserId(userId);

	}

	/**
	 * purpose:method to register a user
	 * 
	 * @param checkUser
	 * @return response
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseDto register(@RequestBody RegistrationDto checkUser) throws UserExceptionHandling, ToDoException {
		logger.info("Register user : {}", checkUser);

		try {
			userService.registerUser(checkUser);
		} catch (MessagingException e) {
			ResponseDto response = new ResponseDto();
			response.setMessage("registration unsucessfull");
		}
		ResponseDto response = new ResponseDto();
		response.setMessage(messages.get("252"));
		response.setStatus(200);
		return response;
	}

	/**
	 * purpose:method to activate the account once registered
	 * 
	 * @param HttpServletRequest
	 * @return response message
	 **/
	@RequestMapping(value = "/activateaccount", method = RequestMethod.GET)
	public ResponseEntity<String> activateAccount(HttpServletRequest request) {
		logger.info("activate the account");
		String jwtToken = request.getQueryString();
		System.out.println(jwtToken);

		if (!userService.activateAc("   " + jwtToken)) {

			return new ResponseEntity<String>(messages.get("254"), HttpStatus.NOT_FOUND);
		}
		String message = messages.get("253");
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	/**
	 * purpose:method to send link if user forgot the password
	 * 
	 * @param CheckUser
	 * @return response
	 * @throws MessagingException
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/forgotpassword/{emailId}", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> forgotPassword(@PathVariable String emailId)
			throws MessagingException, ToDoException {
		if (userService.forgotPassword(emailId)) {
			ResponseDto response = new ResponseDto();
			response.setMessage(messages.get("256"));
			response.setStatus(-3);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.NOT_FOUND);
		}
		ResponseDto response = new ResponseDto();
		response.setMessage(messages.get("255"));
		response.setStatus(200);
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * purpose: method to reset the password
	 * 
	 * @param model
	 * @param request
	 * @return response
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ResponseDto resetPassword(@RequestBody ForgotPasswordDto model, HttpServletRequest req)
			throws ToDoException {
		String token = req.getQueryString();
		//String token = "";
		System.out.println("token from req" + token);
		userService.setPassword(model, token);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("257"));
		return response;

	}

	/**
	 * purpose:method to display all users
	 * 
	 * @return user list
	 */
	@GetMapping(value = "/viewusers")
	public List<User> viewUsers() {
		List<User> users = userService.getAllUsers();
		return users;

	}

	@PostMapping(value = "/createbucket")
	public ResponseEntity<String> ftedt(@RequestParam String bucketName) {

		System.out.println("controller user-----" + bucketName);
		String message = userService.test(bucketName);
		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}

	/**
	 * purpose:method to delete an account
	 * 
	 * @param userId
	 * @return response
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/deleteaccount{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseDto> deleteaccount(@PathVariable String userId) throws ToDoException {
		// String token=request.getQueryString();
		String msg = userService.deleteUser(userId);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(msg);
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

}
