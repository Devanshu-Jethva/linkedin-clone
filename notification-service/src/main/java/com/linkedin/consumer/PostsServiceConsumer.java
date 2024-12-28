package com.linkedin.consumer;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import com.linkedin.clients.ConnectionsClient;
import com.linkedin.dto.PersonDto;
import com.linkedin.event.PostCreatedEvent;
import com.linkedin.event.PostLikedEvent;
import com.linkedin.service.SendNotification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsServiceConsumer {

	private final ConnectionsClient connectionsClient;
	private final SendNotification sendNotification;

//	@KafkaListener(topicPartitions = { @TopicPartition(topic = "post-created-topic", partitionOffsets = {
//			@PartitionOffset(partition = "0", initialOffset = "0"),
//			@PartitionOffset(partition = "1", initialOffset = "0"),
//			@PartitionOffset(partition = "2", initialOffset = "0") }) })
	@KafkaListener(topics = "post-created-topic")
	public void handlePostCreated(final PostCreatedEvent postCreatedEvent) {
		log.info("Sending notifications: handlePostCreated: {}", postCreatedEvent);
		List<PersonDto> connections = connectionsClient.getFirstConnections(postCreatedEvent.getCreatorId());
		log.info("Connections: {}", connections);
		for (PersonDto connection : connections) {
			String message = String.format("Your connection %d has created a post, Check it out",
					postCreatedEvent.getCreatorId());
			log.info("Sending notification to: {}, message: {}", connection, message);
			sendNotification.send(connection.getUserId(), message);
		}
	}

	@KafkaListener(topicPartitions = { @TopicPartition(topic = "post-liked-topic", partitionOffsets = {
			@PartitionOffset(partition = "0", initialOffset = "0"),
			@PartitionOffset(partition = "1", initialOffset = "0"),
			@PartitionOffset(partition = "2", initialOffset = "0") }) })
	public void handlePostLiked(final PostLikedEvent postLikedEvent) {
		log.info("Sending notifications: handlePostLiked: {}", postLikedEvent);
		String message = String.format("Your post, %d has been liked by %d", postLikedEvent.getPostId(),
				postLikedEvent.getLikedByUserId());

		sendNotification.send(postLikedEvent.getCreatorId(), message);
	}

}
