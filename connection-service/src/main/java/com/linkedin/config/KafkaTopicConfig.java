package com.linkedin.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

	@Bean
	NewTopic sendConnectionRequestTopic() {
		return new NewTopic("send-connection-request-topic", 3, (short) 1);
	}

	@Bean
	NewTopic acceptConnectionRequestTopic() {
		return new NewTopic("accept-connection-request-topic", 3, (short) 1);
	}
}
