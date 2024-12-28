package com.linkedin.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.linkedin.event.AcceptConnectionRequestEvent;
import com.linkedin.event.SendConnectionRequestEvent;
import com.linkedin.service.SendNotification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsServiceConsumer {

	private final SendNotification sendNotification;

	@KafkaListener(topics = "send-connection-request-topic")
	public void handleSendConnectionRequest(final SendConnectionRequestEvent sendConnectionRequestEvent) {
		log.info("handle connections: handleSendConnectionRequest: {}", sendConnectionRequestEvent);
		String message = "You have receiver a connection request from user with id: %d"
				+ sendConnectionRequestEvent.getSenderId();
		sendNotification.send(sendConnectionRequestEvent.getReceiverId(), message);
	}

	@KafkaListener(topics = "accept-connection-request-topic")
	public void handleAcceptConnectionRequest(final AcceptConnectionRequestEvent acceptConnectionRequestEvent) {
		log.info("handle connections: handleAcceptConnectionRequest: {}", acceptConnectionRequestEvent);
		String message = "Your connection request has been accepted by the user with id: %d"
				+ acceptConnectionRequestEvent.getReceiverId();
		sendNotification.send(acceptConnectionRequestEvent.getSenderId(), message);
	}

}
