package com.booklovers.book_lovers_project.service;

import java.util.List;

import com.booklovers.book_lovers_project.request.ReviewRequest;
import com.booklovers.book_lovers_project.response.ReviewResponse;

public interface ReviewService {
	ReviewResponse createReview(Integer bookId, String username, ReviewRequest request);

	ReviewResponse updateReview(Integer reviewId, String username, boolean isAdmin, ReviewRequest request);

	void deleteReview(Integer reviewId, String username, boolean isAdmin);

	List<ReviewResponse> getReviewsForBook(Integer bookId);

	long countReviews(Integer bookId);

	Double getAverageRating(Integer bookId);
}