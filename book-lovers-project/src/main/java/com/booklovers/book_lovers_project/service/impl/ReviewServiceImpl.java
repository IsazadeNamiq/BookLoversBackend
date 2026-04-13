package com.booklovers.book_lovers_project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.booklovers.book_lovers_project.entity.BookEntity;
import com.booklovers.book_lovers_project.entity.ReviewEntity;
import com.booklovers.book_lovers_project.entity.UserEntity;
import com.booklovers.book_lovers_project.repository.BookRepository;
import com.booklovers.book_lovers_project.repository.ReviewRepository;
import com.booklovers.book_lovers_project.repository.UserRepository;
import com.booklovers.book_lovers_project.request.ReviewRequest;
import com.booklovers.book_lovers_project.response.ReviewResponse;
import com.booklovers.book_lovers_project.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	public ReviewServiceImpl(ReviewRepository reviewRepository, BookRepository bookRepository,
			UserRepository userRepository) {
		this.reviewRepository = reviewRepository;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}

	@Override
	public ReviewResponse createReview(Integer bookId, String username, ReviewRequest request) {
		BookEntity book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book tapılmadı"));

		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tapılmadı"));

		reviewRepository.findByBook_IdAndUser_Username(bookId, username).ifPresent(r -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bu kitab üçün artıq rəy yazmısan");
		});

		ReviewEntity review = new ReviewEntity();
		review.setBook(book);
		review.setUser(user);
		review.setRating(request.getRating());
		review.setComment(request.getComment());

		return toResponse(reviewRepository.save(review));
	}

	@Override
	public ReviewResponse updateReview(Integer reviewId, String username, boolean isAdmin, ReviewRequest request) {
		ReviewEntity review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rəy tapılmadı"));

		if (!isAdmin && !review.getUser().getUsername().equals(username)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bu rəyi dəyişmək icazən yoxdur");
		}

		review.setRating(request.getRating());
		review.setComment(request.getComment());

		return toResponse(reviewRepository.save(review));
	}

	@Override
	public void deleteReview(Integer reviewId, String username, boolean isAdmin) {
		ReviewEntity review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rəy tapılmadı"));

		if (!isAdmin && !review.getUser().getUsername().equals(username)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bu rəyi silmək icazən yoxdur");
		}

		reviewRepository.delete(review);
	}

	@Override
	public List<ReviewResponse> getReviewsForBook(Integer bookId) {
		return reviewRepository.findByBook_IdOrderByCreatedAtDesc(bookId).stream().map(this::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public long countReviews(Integer bookId) {
		return reviewRepository.countByBook_Id(bookId);
	}

	@Override
	public Double getAverageRating(Integer bookId) {
		Double avg = reviewRepository.getAverageRatingByBookId(bookId);
		return avg == null ? 0.0 : avg;
	}

	private ReviewResponse toResponse(ReviewEntity review) {
		ReviewResponse response = new ReviewResponse();
		response.setId(review.getId());
		response.setBookId(review.getBook().getId());
		response.setBookTitle(review.getBook().getTitle());
		response.setUserId(review.getUser().getId());
		response.setUsername(review.getUser().getUsername());
		response.setRating(review.getRating());
		response.setComment(review.getComment());
		response.setCreatedAt(review.getCreatedAt());
		response.setUpdatedAt(review.getUpdatedAt());
		return response;
	}
}