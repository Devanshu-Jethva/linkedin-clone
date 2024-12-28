package com.linkedin.service;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.linkedin.auth.UserContextHolder;
import com.linkedin.entity.Person;
import com.linkedin.event.AcceptConnectionRequestEvent;
import com.linkedin.event.SendConnectionRequestEvent;
import com.linkedin.exception.BadRequestException;
import com.linkedin.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConnectionsService {

	private final PersonRepository personRepository;
	private final KafkaTemplate<Long, SendConnectionRequestEvent> sendRequestKafkaTemplate;
	private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptRequestKafkaTemplate;

	public List<Person> getFirstDegreeConnections() {
		Long userId = UserContextHolder.getCurrentUserId();
		log.info("Getting first degree connections for user with id: {}", userId);

		return personRepository.getFirstDegreeConnections(userId);
	}

	public Boolean sendConnectionRequest(final Long receiverId) {
		Long senderId = UserContextHolder.getCurrentUserId();
		log.info("Trying to send connection request, sender: {}, reciever: {}", senderId, receiverId);

		if (senderId.equals(receiverId)) {
			throw new BadRequestException("Both sender and receiver are the same");
		}

		boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
		if (alreadySentRequest) {
			throw new BadRequestException("Connection request already exists, cannot send again");
		}

		boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
		if (alreadyConnected) {
			throw new BadRequestException("Already connected users, cannot add connection request");
		}

		log.info("Successfully sent the connection request");
		personRepository.addConnectionRequest(senderId, receiverId);

		SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder().senderId(senderId)
				.receiverId(receiverId).build();

		sendRequestKafkaTemplate.send("send-connection-request-topic", sendConnectionRequestEvent);

		return true;
	}

	public Boolean acceptConnectionRequest(final Long senderId) {
		Long receiverId = UserContextHolder.getCurrentUserId();

		boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
		if (!connectionRequestExists) {
			throw new BadRequestException("No connection request exists to accept");
		}

		personRepository.acceptConnectionRequest(senderId, receiverId);
		log.info("Successfully accepted the connection request, sender: {}, receiver: {}", senderId, receiverId);

		AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder()
				.senderId(senderId).receiverId(receiverId).build();

		acceptRequestKafkaTemplate.send("accept-connection-request-topic", acceptConnectionRequestEvent);
		return true;
	}

	public Boolean rejectConnectionRequest(final Long senderId) {
		Long receiverId = UserContextHolder.getCurrentUserId();

		boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
		if (!connectionRequestExists) {
			throw new BadRequestException("No connection request exists, cannot delete");
		}

		personRepository.rejectConnectionRequest(senderId, receiverId);
		return true;
	}
}
