/*package com.todo.user.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SqsConfig {
	@Value("${queue.endpoint}")
	private String endpoint;
	@Value("${queue.name}")
	private String queueName;

	@SuppressWarnings("deprecation")
	@Bean
	public AmazonSQSClient createSQSClient() {
		@SuppressWarnings("deprecation")
		AmazonSQSClient amazonSQSClient = new AmazonSQSClient(new BasicAWSCredentials("cr", "cr"));
		amazonSQSClient.setEndpoint(endpoint);
		amazonSQSClient.createQueue(queueName);
		return amazonSQSClient;
	}
}*/