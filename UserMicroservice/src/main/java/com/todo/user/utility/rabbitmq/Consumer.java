package com.todo.user.utility.rabbitmq;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.user.dto.MailModel;
import com.todo.user.utility.email.EmailService;
/*************************************************************************************************************
*
* purpose:Rabbitmq Reciver
* 
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
**************************************************************************************************/
@Service
public class Consumer {
	@Autowired
	EmailService emailService;

	@RabbitListener(queues = "${todo.rabbitmq.queue}")
	public void reciveMsg(MailModel mail) throws MessagingException {
		emailService.sendMail(mail);
	}

}
