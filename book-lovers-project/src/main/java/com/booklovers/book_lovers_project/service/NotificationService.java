package com.booklovers.book_lovers_project.service;

import java.util.List;

import com.booklovers.book_lovers_project.entity.NotificationType;
import com.booklovers.book_lovers_project.response.NotificationResponse;

public interface NotificationService {
	NotificationResponse createNotification(String username, NotificationType type, String title, String message,
			Integer relatedEntityId);

	List<NotificationResponse> getMyNotifications(String username);

	long getUnreadCount(String username);

	NotificationResponse markAsRead(Integer notificationId, String username, boolean isAdmin);

	void createDueSoonLoanNotifications();
}