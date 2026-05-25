package com.booklovers.book_lovers_project.response;

import java.util.List;

public class DashboardStatsResponse {

	private long totalBooks;
	private long totalUsers;
	private long totalCategories;
	private long totalReviews;
	private long totalFavorites;
	private long activeLoans;

	private List<BookStatResponse> topFavoritedBooks;
	private List<BookStatResponse> topRatedBooks;

	public long getTotalBooks() {
		return this.totalBooks;
	}

	public void setTotalBooks(long totalBooks) {
		this.totalBooks = totalBooks;
	}

	public long getTotalUsers() {
		return this.totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getTotalCategories() {
		return this.totalCategories;
	}

	public void setTotalCategories(long totalCategories) {
		this.totalCategories = totalCategories;
	}

	public long getTotalReviews() {
		return this.totalReviews;
	}

	public void setTotalReviews(long totalReviews) {
		this.totalReviews = totalReviews;
	}

	public long getTotalFavorites() {
		return this.totalFavorites;
	}

	public void setTotalFavorites(long totalFavorites) {
		this.totalFavorites = totalFavorites;
	}

	public long getActiveLoans() {
		return this.activeLoans;
	}

	public void setActiveLoans(long activeLoans) {
		this.activeLoans = activeLoans;
	}

	public List<BookStatResponse> getTopFavoritedBooks() {
		return this.topFavoritedBooks;
	}

	public void setTopFavoritedBooks(List<BookStatResponse> topFavoritedBooks) {
		this.topFavoritedBooks = topFavoritedBooks;
	}

	public List<BookStatResponse> getTopRatedBooks() {
		return this.topRatedBooks;
	}

	public void setTopRatedBooks(List<BookStatResponse> topRatedBooks) {
		this.topRatedBooks = topRatedBooks;
	}
}