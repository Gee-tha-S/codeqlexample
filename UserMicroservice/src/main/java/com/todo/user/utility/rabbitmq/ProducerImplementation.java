package com.todo.user.utility.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.todo.user.dto.MailModel;

/*************************************************************************************************************
*
* purpose:Producer implementation
* 
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
**************************************************************************************************/
@Service
public class ProducerImplementation implements Producer {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${todo.rabbitmq.exchange}")
	private String exchange;

	@Value("${todo.rabbitmq.routingkey}")
	private String routingKey;

	@Override
	public void produceMail(MailModel mail) {
		amqpTemplate.convertAndSend(exchange, routingKey, mail);
		System.out.println("Send mail = " + mail);
	}
}
