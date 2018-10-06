package com.todo.user.service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.todo.user.dto.ForgotPasswordDto;
import com.todo.user.dto.MailModel;
import com.todo.user.dto.RegistrationDto;
import com.todo.user.model.User;
import com.todo.user.repository.UserRepository;
import com.todo.user.security.JwtTokens;
import com.todo.user.utility.Messages;
import com.todo.user.utility.Utility;
import com.todo.user.utility.exceptions.LoginExceptionHandling;
import com.todo.user.utility.exceptions.ToDoException;
import com.todo.user.utility.exceptions.UserExceptionHandling;
import com.todo.user.utility.rabbitmq.Producer;

import io.jsonwebtoken.Claims;

/*************************************************************************************************************
 *
 * purpose:User Service implementation
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

@Service
public class UserServiceImplementation implements UserService, FeingService {

	@Autowired
	UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	//@Autowired
	//private EmailService emailService;
	@Autowired
	private MailModel mailDto;
	@Autowired
	private Producer produce;
	@Autowired
	private ModelMapper mapper;
	@Value("${host}")
	private String host;
	@Autowired
	private JwtTokens tokens;
	@Autowired
	private RedisRepository redisrepo;
	@Autowired
	private FeingService fservice;
	//@Autowired
	//private SqsService sqs;
	//@Autowired
	//private Sender sender;
	@Autowired
	private ElasticService eServices;

	// @Autowired
	// private MessageService messageService;

	@Autowired
	Messages messages;
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

	Utility utility = new Utility();

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * purpose : method to login to account
	 * 
	 * @param emailId
	 * @param password
	 * @return token
	 * @throws LoginExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException
	 * @throws UserExceptionHandling 
	 */
	@Override
	public String login(String emailId, String password)
			throws LoginExceptionHandling, MessagingException, ToDoException, UserExceptionHandling {

		logger.info("login");

		utility.checkNotNull(emailId, "emailId should not be blank");
		utility.checkNotNull(password, "password should not be blank");
		Optional<User> user = repo.findByEmailId(emailId);
		utility.isPresentInDb(user.isPresent(), messages.get("101"));
		
		User curUser = user.get();
		String jwtToken = tokens.createToken(curUser);
		Claims claims = tokens.parseJwt(jwtToken);
		String id = claims.getSubject();
		String redisuserId = redisrepo.getUserId("userId");
		utility.isPresentInDb(user.get().isActivate(), "account is not activated");
		if (encoder.matches(password, user.get().getPassword())) {

			logger.info("looged in sucessfully!! continue your works");

			System.out.println("redis check" + id + "  " + redisuserId);

			return jwtToken;

		} else {
			throw new ToDoException(messages.get("102"));
		}

	}

	/**
	 * purpose:method to register
	 * 
	 * @param user
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException
	 */

	@Override
	public void registerUser(RegistrationDto userReg) throws UserExceptionHandling, MessagingException, ToDoException {
		logger.info("checking the fields are valid or not");
		if (!utility.isValidateAllFields(userReg)) {
			System.out.println(userReg.getEmailId());

			 Optional<User> checkUser = repo.findByEmailId(userReg.getEmailId());
			mapper.map(userReg, User.class);
		 utility.isPresentInDb(!checkUser.isPresent(), messages.get("103"));
			userReg.setPassword(encoder.encode(userReg.getPassword()));
		}

			User user = new User();
		    user = mapper.map(userReg, User.class);
			String id=user.setId(sdf.format(new Date()));
			System.out.println(id);

			//user = repo.save(user);
			System.out.println(user);
			System.out.println(userReg);
		
			eServices.insertUser(user);
			repo.save(user);
			// System.out.println(users);

			//String path = user.getProfilePic();
			// System.out.println("creating folder");
			//AwsServices.createFolder("bridgelabz-todo-storage", user.getUserName());
			System.out.println("created folder");

			//AwsServices.uploadFile("bridgelabz-todo-storage", user.getUserName(), path);
			// System.out.println("wqs");
			logger.info("user", user);
			String token = tokens.createToken(user);
			Claims claims = tokens.parseJwt(token);
			String userId = claims.getSubject();
			redisrepo.setUserId(userId);
			mailDto.setToMailAddress(userReg.getEmailId());
			mailDto.setSubject("Hi " + userReg.getUserName());
			mailDto.setBody(messages.get("activation.link") + host + messages.get("601") + token);
			produce.produceMail(mailDto);
			// saveObject(user);

		
	}
	

	/**
	 * purpose: method to activate the account
	 * 
	 * @param jwt
	 * @return boolean
	 */
	@Override
	public boolean activateAc(String jwt) {

		Claims claims = tokens.parseJwt(jwt);
		Optional<User> user = repo.findById(claims.getSubject());
		user.get().setActivate(true);
		logger.debug("account activated");
		repo.save(user.get());

		return true;

	}

	/**
	 * purpose:method to set the password
	 * 
	 * @param forgotpassword
	 *            dto
	 * @param tokenJwt
	 * @throws ToDoException
	 */

	@Override
	public void setPassword(ForgotPasswordDto f, String tokenJwt) throws ToDoException {
		System.out.println(tokenJwt);
		if (!f.getNewPassword().equals(f.getNewPassword())) {
			throw new ToDoException(messages.get("104"));
		}
		Claims claims = tokens.parseJwt(tokenJwt);

		Optional<User> checkUser = repo.findByEmailId(claims.getId());
		System.out.println(claims.getSubject());
		User user = checkUser.get();
		user.setPassword(encoder.encode(f.getConfirmPassword()));
		repo.save(user);
	}

	/**
	 * purpose: method to send link to reset password if user forgot password
	 * 
	 * @param emailId
	 * @return boolean
	 * @throws MessagingException
	 * @throws ToDoException
	 */
	@Override
	public boolean forgotPassword(String emailId) throws MessagingException, ToDoException {
		Optional<User> checkUser = repo.findByEmailId(emailId);
		utility.isPresentInDb(checkUser.isPresent(), messages.get("101"));
		User user = checkUser.get();
		String jwtToken = tokens.createToken(user);

		// sqs.recivemsg();

		mailDto.setToMailAddress(emailId);
		System.out.println(emailId);
		mailDto.setSubject(messages.get("forgotPassword.subject"));
		mailDto.setBody(messages.get("resetPassword.link") + host + messages.get("600") + jwtToken);

		//sender.produceMail((messages.get("resetPassword.link") + host + messages.get("600") + jwtToken), mailDto);

		/// sqs.produceMail(mailDto);
		/// sqs.getMessage();
		// messageService.sendMessage(messages.get("resetPassword.link") + host +
		/// messages.get("600") + jwtToken);

		 produce.produceMail(mailDto);
		return false;
	}

	/**
	 * purpose:method to display all users
	 * 
	 * @return user list
	 */
	@Override
	public List<User> getAllUsers() {
		List<User> users = repo.findAll();
		return users;
	}

	/**
	 * purpose:method to delete an account
	 * 
	 * @param userId
	 * @return message
	 * @throws ToDoException
	 */
	@Override
	public String deleteUser(String id) throws ToDoException {
		// Claims claims = tokens.parseJwt(token);

		// if (claims.getId() == id) {
		Optional<User> checkUser = repo.findById(id);
		utility.isPresentInDb(checkUser.isPresent(), messages.get("101"));
		repo.deleteById(id);
		return "user deleted successfully";
		// }
		// return "authentication failed";
	}

	@Override
	public String test(String bucketName) {

		String d = fservice.createBucket(bucketName);
		return d;

	}
	
	/*
	 * @Override public String CreateBucket(String bucketName) {
	 * 
	 * String bn=fservice.CreateBucket(bucketName);
	 * 
	 * return bn; }
	 */

	@Override
	public String createBucket(String bucketName) {
		String d = fservice.createBucket(bucketName);

		// TODO Auto-generated method stub
		return d;
	}

	/*
	 * @Override public Object saveObject(Object ob) {
	 * 
	 * return fservice.saveObject(ob); }
	 */
}
