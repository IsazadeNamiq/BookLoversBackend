package com.booklovers.book_lovers_project.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booklovers.book_lovers_project.request.ReviewRequest;
import com.booklovers.book_lovers_project.response.ReviewResponse;
import com.booklovers.book_lovers_project.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books/{bookId}/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<ReviewResponse> createReview(@PathVariable Integer bookId,
			@Valid @RequestBody ReviewRequest request, Principal principal) {

		return ResponseEntity.ok(reviewService.createReview(bookId, principal.getName(), request));
	}

	@GetMapping
	public ResponseEntity<List<ReviewResponse>> getBookReviews(@PathVariable Integer bookId) {
		return ResponseEntity.ok(reviewService.getReviewsForBook(bookId));
	}

	@GetMapping("/summary")
	public ResponseEntity<Map<String, Object>> summary(@PathVariable Integer bookId) {
		Map<String, Object> body = new HashMap<>();
		body.put("bookId", bookId);
		body.put("reviewCount", reviewService.countReviews(bookId));
		body.put("averageRating", reviewService.getAverageRating(bookId));
		return ResponseEntity.ok(body);
	}

	@PutMapping("/{reviewId}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<ReviewResponse> updateReview(@PathVariable Integer bookId, @PathVariable Integer reviewId,
			@Valid @RequestBody ReviewRequest request, Authentication authentication) {

		boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

		return ResponseEntity.ok(reviewService.updateReview(reviewId, authentication.getName(), isAdmin, request));
	}

	@DeleteMapping("/{reviewId}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Void> deleteReview(@PathVariable Integer bookId, @PathVariable Integer reviewId,
			Authentication authentication) {

		boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

		reviewService.deleteReview(reviewId, authentication.getName(), isAdmin);
		return ResponseEntity.noContent().build();
	}
}