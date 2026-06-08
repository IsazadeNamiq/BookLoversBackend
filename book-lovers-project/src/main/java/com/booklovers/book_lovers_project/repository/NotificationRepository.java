package com.booklovers.book_lovers_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.book_lovers_project.entity.NotificationEntity;
import com.booklovers.book_lovers_project.entity.NotificationStatus;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

	List<NotificationEntity> findByUser_UsernameOrderByCreatedAtDesc(String username);

	List<NotificationEntity> findByUser_UsernameAndStatusOrderByCreatedAtDesc(String username,
			NotificationStatus status);

	long countByUser_UsernameAndStatus(String username, NotificationStatus status);

	Optional<NotificationEntity> findByNotificationKey(String notificationKey);
}