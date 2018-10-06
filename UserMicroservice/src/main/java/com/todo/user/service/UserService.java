package com.todo.user.service;

import java.util.List;

import javax.mail.MessagingException;

import com.todo.user.dto.ForgotPasswordDto;
import com.todo.user.dto.RegistrationDto;
import com.todo.user.model.User;
import com.todo.user.utility.exceptions.LoginExceptionHandling;
import com.todo.user.utility.exceptions.ToDoException;
import com.todo.user.utility.exceptions.UserExceptionHandling;

/*************************************************************************************************************
 *
 * purpose:User Service 
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

public interface UserService {

	/**
	 * purpose : method to login to account
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 * @throws LoginExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException
	 * @throws UserExceptionHandling 
	 */
	public String login(String emailId, String password)
			throws LoginExceptionHandling, MessagingException, ToDoException, UserExceptionHandling;

	/**
	 * purpose:method to register
	 * 
	 * @param userReg
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException
	 */
	public void registerUser(RegistrationDto userReg) throws UserExceptionHandling, MessagingException, ToDoException;

	/**
	 * purpose: method to activate the account
	 * 
	 * @param jwt
	 * @return
	 */
	public boolean activateAc(String jwt);

	/**
	 * purpose:method to set the password
	 * 
	 * @param forgotpassword
	 *            model
	 * @param tokenJwt
	 * @throws ToDoException
	 */
	public void setPassword(ForgotPasswordDto f, String tokenJwt) throws ToDoException;

	/**
	 * purpose:method to send link to reset password if user forgot password
	 * 
	 * @param emailId
	 * @return
	 * @throws MessagingException
	 * @throws ToDoException
	 */
	public boolean forgotPassword(String emailId) throws MessagingException, ToDoException;

	/**
	 * purpose:method to display all users
	 * 
	 * @return
	 */
	List<User> getAllUsers();

	/**
	 * purpose:method to delete an account
	 * 
	 * @param id
	 * @return
	 * @throws ToDoException
	 */
	String deleteUser(String id) throws ToDoException;



	String test(String bucketName);

	//void registerUser(User userReg) throws UserExceptionHandling, MessagingException, ToDoException;

}
