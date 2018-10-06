package com.todo.user.utility.rabbitmq;

import com.todo.user.dto.MailModel;
/*************************************************************************************************************
*
* purpose:Producer Service 
* 
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
**************************************************************************************************/
public interface Producer 
{
	void produceMail(MailModel model);

}
