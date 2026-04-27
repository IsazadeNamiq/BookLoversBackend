package com.booklovers.book_lovers_project.response;

import java.time.LocalDateTime;

import com.booklovers.book_lovers_project.entity.LoanStatus;

public class LoanResponse {

	private Integer id;
	private Integer bookId;
	private String bookTitle;
	private Integer userId;
	private String username;
	private LocalDateTime borrowedAt;
	private LocalDateTime dueDate;
	private LocalDateTime returnedAt;
	private LoanStatus status;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return this.bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getBorrowedAt() {
		return this.borrowedAt;
	}

	public void setBorrowedAt(LocalDateTime borrowedAt) {
		this.borrowedAt = borrowedAt;
	}

	public LocalDateTime getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDateTime getReturnedAt() {
		return this.returnedAt;
	}

	public void setReturnedAt(LocalDateTime returnedAt) {
		this.returnedAt = returnedAt;
	}

	public LoanStatus getStatus() {
		return this.status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}
}