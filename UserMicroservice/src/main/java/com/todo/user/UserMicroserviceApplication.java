package com.todo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*************************************************************************************************************
 *
 * purpose:User Micro service
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 28-07-18
 *
 **************************************************************************************************/

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "UserMicroservice")
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

}
