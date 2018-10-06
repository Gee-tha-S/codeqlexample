/*package com.todo.user.sqs;

import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;

@Component
public class MailSender {

	static AWSCredentials credentials = new BasicAWSCredentials("cr",
			"cr");
	

	static AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_2)
			.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

	public void send()
	{
		SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
		        .withQueueUrl("url")
		        .withEntries(
		                new SendMessageBatchRequestEntry(
		                        "msg_1", "Hello from message 1"),
		                new SendMessageBatchRequestEntry(
		                        "msg_2", "Hello from message 2")
		                        .withDelaySeconds(10));
		amazonSQS.sendMessageBatch(send_batch_request);

	}
	public void delete()
	{
        List<Message> messages = amazonSQS.receiveMessage("url").getMessages();

        // delete messages from the queue
        for (Message m : messages) {
        	System.out.println(m);
        	amazonSQS.deleteMessage("url", m.getReceiptHandle());
	}
	}
}
*/