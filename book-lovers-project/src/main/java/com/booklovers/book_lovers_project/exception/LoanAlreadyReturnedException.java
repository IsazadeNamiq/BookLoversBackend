package com.booklovers.book_lovers_project.exception;

public class LoanAlreadyReturnedException extends RuntimeException {
	public LoanAlreadyReturnedException(String message) {
		super(message);
	}
}