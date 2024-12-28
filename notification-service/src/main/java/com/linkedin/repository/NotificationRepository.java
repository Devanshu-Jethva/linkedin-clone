package com.linkedin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkedin.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
