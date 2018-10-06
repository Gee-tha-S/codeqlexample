package com.todo.user.utility.email;

import javax.mail.MessagingException;

import com.todo.user.dto.MailModel;


/*************************************************************************************************************
 *
 * purpose:Email Service
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

public interface EmailService {

	//void sendMail(String to,String subject,String body) throws MessagingException;

	void sendMail(MailModel mail) throws MessagingException;
}