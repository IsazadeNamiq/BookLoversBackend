package com.booklovers.book_lovers_project.response;

import java.time.LocalDateTime;

import com.booklovers.book_lovers_project.entity.NotificationStatus;
import com.booklovers.book_lovers_project.entity.NotificationType;

public class NotificationResponse {

	private Integer id;
	private NotificationType type;
	private String title;
	private String message;
	private NotificationStatus status;
	private Integer relatedEntityId;
	private LocalDateTime createdAt;
	private LocalDateTime readAt;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public NotificationType getType() {
		return this.type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NotificationStatus getStatus() {
		return this.status;
	}

	public void setStatus(NotificationStatus status) {
		this.status = status;
	}

	public Integer getRelatedEntityId() {
		return this.relatedEntityId;
	}

	public void setRelatedEntityId(Integer relatedEntityId) {
		this.relatedEntityId = relatedEntityId;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getReadAt() {
		return this.readAt;
	}

	public void setReadAt(LocalDateTime readAt) {
		this.readAt = readAt;
	}
}