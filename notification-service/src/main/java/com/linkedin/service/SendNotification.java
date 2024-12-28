package com.linkedin.service;

import org.springframework.stereotype.Service;

import com.linkedin.entity.Notification;
import com.linkedin.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendNotification {

	private final NotificationRepository notificationRepository;

	public void send(final Long userId, final String message) {
		Notification notification = new Notification();
		notification.setMessage(message);
		notification.setUserId(userId);

		notificationRepository.save(notification);
		log.info("Notification saved for user: {}", userId);
	}
}