package com.todo.user.utility;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.lang.Nullable;

import com.todo.user.dto.RegistrationDto;
import com.todo.user.model.User;
import com.todo.user.utility.exceptions.ToDoException;
import com.todo.user.utility.exceptions.UserExceptionHandling;

/*************************************************************************************************************
 *
 * purpose:Utility class
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

public class Utility {

	public Utility() {

	}

	/**
	 * method validate the fields when registering
	 * 
	 * @param register
	 * @return
	 * @throws UserExceptionHandling
	 */

	public boolean isValidateAllFields(RegistrationDto register) throws UserExceptionHandling {
		if (!validateEmailAddress(register.getEmailId())) {
			throw new UserExceptionHandling("emailid not valid");
		} else if (!isValidUserName(register.getUserName())) {
			throw new UserExceptionHandling("UserName Not valid");
		} else if (!validatePassword(register.getPassword())) {
			throw new UserExceptionHandling("password not valid");
		} else if (!isValidMobileNumber(register.getPhoneNumber())) {
			throw new UserExceptionHandling("mobile number not valid");
		}
		return false;
	}

	public static boolean validateEmailAddress(String emailId) {
		Pattern emailNamePtrn = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mtch = emailNamePtrn.matcher(emailId);
		return mtch.matches();

	}

	public static boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();

	}

	public static boolean isValidUserName(String userName) {
		Pattern userNamePattern = Pattern.compile("^[a-z0-9_-]{6,14}$");
		System.err.println(userName);
		Matcher matcher = userNamePattern.matcher(userName);
		return matcher.matches();

	}

	public static boolean isValidMobileNumber(String mobileNumber) {
		Pattern mobileNumberPattern = Pattern.compile("\\d{10}");
		Matcher matcher = mobileNumberPattern.matcher(mobileNumber);
		return matcher.matches();
	}

	public <T> T checkNotNull(T resource,Object erroemsg) throws UserExceptionHandling {
		if (resource == "") {
			throw new UserExceptionHandling(String.valueOf(erroemsg));

		}
		return resource;

	}

	public <T> T CheckNull(T resource) throws UserExceptionHandling {
		if (resource == null) {
			throw new UserExceptionHandling(("user with this id does not exist"));
		}
		return resource;
	}

	public <T> T CheckPassword(T resource) throws UserExceptionHandling {
		if (resource == null) {
			throw new UserExceptionHandling(("invalid password"));
		}
		return resource;
	}

	public boolean isPresentInDb(boolean reference, @Nullable Object errorMessage) throws ToDoException {
		if (!reference) {
			throw new ToDoException(String.valueOf(errorMessage));
		}
		return reference;
		
		
	}
	

	
}
