package com.booklovers.book_lovers_project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booklovers.book_lovers_project.response.NotificationResponse;
import com.booklovers.book_lovers_project.service.NotificationService;

@RestController
@RequestMapping("/api")
public class NotificationController {

	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@GetMapping("/users/me/notifications")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<NotificationResponse>> myNotifications(Principal principal) {
		return ResponseEntity.ok(notificationService.getMyNotifications(principal.getName()));
	}

	@GetMapping("/users/me/notifications/unread-count")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Long> unreadCount(Principal principal) {
		return ResponseEntity.ok(notificationService.getUnreadCount(principal.getName()));
	}

	@PostMapping("/notifications/{id}/read")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Integer id, Authentication authentication) {
		boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

		return ResponseEntity.ok(notificationService.markAsRead(id, authentication.getName(), isAdmin));
	}
}