/*package com.bridgelabz.userservice.sqs;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

*//**********************************************************************
 * @author Ankita Kalgutkar
 *
 * 18-May-2018
 * 
 *************************************************************************//*

@Service
public class SQSProducer
{
    @Autowired
    private JmsTemplate jmsTemplate;

    private String queueName ="message";

    public void sendMessage(String message)
    {
        jmsTemplate.send(queueName, new MessageCreator() 
        {
            @Override
            public Message createMessage(Session session) throws JMSException 
            {

                return session.createTextMessage(message);
            }
        });
    }
}*/