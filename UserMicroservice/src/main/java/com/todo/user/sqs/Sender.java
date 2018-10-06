/*package com.todo.user.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.todo.user.dto.MailModel;

@Service
public class Sender {
	@Autowired
	Reciver reciver;
	static final String queueUrl="https://sqs.us-east-2.amazonaws.com/228059415208/todo";

	static AWSCredentials credentials = new BasicAWSCredentials(System.getenv("SQS-ACCESS-KEY-ID"),
			System.getenv("SQS-SECRET-KEY"));

	static AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_2)
			.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

	public void produceMail(String message, MailModel mailDto) {
		try {
			System.out.println(message);
			SendMessageRequest send_msg_request = new SendMessageRequest()
					.withQueueUrl(queueUrl).withMessageBody(message);
			SendMessageResult tgh = amazonSQS.sendMessage(send_msg_request);
			// emailService.sendMail(to, subject, body);

			// amazonSQS.sendMessage(send_batch_request);
			System.out.println("sent");
			reciver.getMessage(mailDto);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
*/