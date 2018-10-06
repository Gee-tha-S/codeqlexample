/*package com.bridgelabz.userservice.sqs;

import java.io.IOException;

*//********************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *********************************************************************************//*

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bridgelabz.userservice.model.Mailmodel;
import com.bridgelabz.userservice.utility.MailSender;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SQSListener implements MessageListener 
{

    @Autowired
    private MailSender mailService;

    @Override
    public void onMessage(Message message)
    {
    	System.out.println("In SQS Listener");
        TextMessage msg = (TextMessage) message;
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONParser parser = new JSONParser();
            JSONObject json = null;
            try 
            {
                json = (JSONObject) parser.parse(msg.getText());
            } 
            catch (ParseException e) 
            {
                e.printStackTrace();
            }
           
            Mailmodel mail = objectMapper.readValue(json.toJSONString(),Mailmodel.class);
           System.out.println(mail.getSubject());
            mailService.sendMail(mail);

        } 
        catch (MessagingException | IOException e)
        {

            e.printStackTrace();
        }
        catch (JMSException e) 
        {
            e.printStackTrace();
        }
    }
}

*/