package com.todo.user.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.todo.user.model.User;

/*************************************************************************************************************
 *
 * purpose:user configuration
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 19-07-18
 *
 **************************************************************************************************/

@Configuration
public class UserConfiguration {
	/**
	 * Password encoder
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * modelMapper configuration
	 * 
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	
	/**
	 * reddis configuration
	 * 
	 * @return
	 */
	
	@SuppressWarnings("deprecation")
	@Bean
	public JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(6379);
		return connectionFactory;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate<String, User> redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(connectionFactory());
		return redisTemplate;
	}

	/**
	 * rabbitmq configuration
	 * 
	 */
	@Value("")
	String queueName;

	@Value("")
	String exchanger;

	@Value("")
	private String routingKey;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchanger);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
			SimpleRabbitListenerContainerFactoryConfigurer configurer) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setMessageConverter(jsonMessageConverter());
		return factory;
	}

	/**
	 * @return properties of the desired profile
	 */
	/*
	 * @Value("${profile}") private static String activeProfile;
	 * 
	 * @Bean public String test() { System.out.println(activeProfile); return
	 * activeProfile;
	 * 
	 * }
	 */

	/*
	 * @Bean public static PropertySourcesPlaceholderConfigurer propertyConfigurer()
	 * { Resource resource; String activeProfile;
	 * 
	 * PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
	 * new PropertySourcesPlaceholderConfigurer();
	 * 
	 * // get active profilesssssss
	 * activeProfile=System.getProperty("spring.profiles.active");
	 * 
	 * // choose different property files for different active profile if
	 * ("development".equals(activeProfile)) { resource = new
	 * ClassPathResource("/static/files/applicationdevelopment.properties");
	 * System.out.println("currently in " + activeProfile + " profile"); } else if
	 * ("test".equals(activeProfile)) { resource = new
	 * ClassPathResource("/static/files/applicationtest.properties");
	 * System.out.println("currently in " + activeProfile + " profile"); } else {
	 * resource = new
	 * ClassPathResource("static/files/applicationproduction.properties");
	 * System.out.println("currently in " + activeProfile + " profile"); }
	 * 
	 * // load the property file
	 * propertySourcesPlaceholderConfigurer.setLocation(resource);
	 * 
	 * return propertySourcesPlaceholderConfigurer; }
	 */
}
