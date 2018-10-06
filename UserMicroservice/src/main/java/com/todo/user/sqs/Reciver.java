/*package com.todo.user.sqs;


import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.todo.user.dto.MailModel;
import com.todo.user.utility.email.EmailService;

@Service
public class Reciver {
	@Autowired
	EmailService emailService;
	static final String queueUrl = "https://sqs.us-east-2.amazonaws.com/228059415208/todo";
	static AWSCredentials credentials = new BasicAWSCredentials(System.getenv("SQS-ACCESS-KEY-ID"),
			System.getenv("SQS-SECRET-KEY"));
	static AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_2)
			.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

	public void getMessage(MailModel mailDto) throws MessagingException {
		ReceiveMessageRequest req = new ReceiveMessageRequest();
		req.setQueueUrl(queueUrl);
		req.setMaxNumberOfMessages(3);
		req.setVisibilityTimeout(100000000);
		ReceiveMessageResult result = amazonSQS.receiveMessage(req);
		
		 * List<Message> messages = amazonSQS.receiveMessage(
		 * "url") .getMessages();
		 
		result.getMessages();
		System.out.println(result.getMessages());
		for (Message m : result.getMessages()) {

			System.out.println(m.getBody());
			System.out.println("sqs---" + mailDto.getToMailAddress());
			//emailService.sendMail(mailDto.getToMailAddress(), mailDto.getSubject(), m.getBody());
		}

	}

}
*/