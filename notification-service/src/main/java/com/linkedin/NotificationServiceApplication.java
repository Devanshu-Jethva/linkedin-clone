package com.linkedin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NotificationServiceApplication {

	public static void main(final String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
