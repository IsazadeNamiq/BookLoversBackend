package com.booklovers.book_lovers_project.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.booklovers.book_lovers_project.entity.LoanEntity;
import com.booklovers.book_lovers_project.entity.LoanStatus;
import com.booklovers.book_lovers_project.entity.NotificationEntity;
import com.booklovers.book_lovers_project.entity.NotificationStatus;
import com.booklovers.book_lovers_project.entity.NotificationType;
import com.booklovers.book_lovers_project.entity.UserEntity;
import com.booklovers.book_lovers_project.exception.ResourceNotFoundException;
import com.booklovers.book_lovers_project.repository.LoanRepository;
import com.booklovers.book_lovers_project.repository.NotificationRepository;
import com.booklovers.book_lovers_project.repository.UserRepository;
import com.booklovers.book_lovers_project.response.NotificationResponse;
import com.booklovers.book_lovers_project.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;
	private final LoanRepository loanRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository,
			LoanRepository loanRepository) {
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
		this.loanRepository = loanRepository;
	}

	@Override
	public NotificationResponse createNotification(String username, NotificationType type, String title, String message,
			Integer relatedEntityId) {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

		String key = relatedEntityId == null ? UUID.randomUUID().toString() : type.name() + "_" + relatedEntityId;

		return notificationRepository.findByNotificationKey(key).map(this::toResponse).orElseGet(() -> {
			NotificationEntity notification = new NotificationEntity();
			notification.setNotificationKey(key);
			notification.setUser(user);
			notification.setType(type);
			notification.setTitle(title);
			notification.setMessage(message);
			notification.setRelatedEntityId(relatedEntityId);
			notification.setStatus(NotificationStatus.UNREAD);
			return toResponse(notificationRepository.save(notification));
		});
	}

	@Override
	public List<NotificationResponse> getMyNotifications(String username) {
		return notificationRepository.findByUser_UsernameOrderByCreatedAtDesc(username).stream().map(this::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public long getUnreadCount(String username) {
		return notificationRepository.countByUser_UsernameAndStatus(username, NotificationStatus.UNREAD);
	}

	@Override
	public NotificationResponse markAsRead(Integer notificationId, String username, boolean isAdmin) {
		NotificationEntity notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));

		if (!isAdmin && !notification.getUser().getUsername().equals(username)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bu bildirişi dəyişmək icazən yoxdur");
		}

		notification.setStatus(NotificationStatus.READ);
		notification.setReadAt(LocalDateTime.now());

		return toResponse(notificationRepository.save(notification));
	}

	@Override
	public void createDueSoonLoanNotifications() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime threeDaysLater = now.plusDays(3);

		List<LoanEntity> loans = loanRepository.findByStatusAndDueDateBetween(LoanStatus.BORROWED, now, threeDaysLater);

		for (LoanEntity loan : loans) {
			String title = "Kitabın qaytarılma tarixi yaxınlaşır";
			String message = "“" + loan.getBook().getTitle() + "” kitabının qaytarılma tarixi: " + loan.getDueDate();

			createNotification(loan.getUser().getUsername(), NotificationType.LOAN_DUE_SOON, title, message,
					loan.getId());
		}
	}

	private NotificationResponse toResponse(NotificationEntity notification) {
		NotificationResponse response = new NotificationResponse();
		response.setId(notification.getId());
		response.setType(notification.getType());
		response.setTitle(notification.getTitle());
		response.setMessage(notification.getMessage());
		response.setStatus(notification.getStatus());
		response.setRelatedEntityId(notification.getRelatedEntityId());
		response.setCreatedAt(notification.getCreatedAt());
		response.setReadAt(notification.getReadAt());
		return response;
	}
}