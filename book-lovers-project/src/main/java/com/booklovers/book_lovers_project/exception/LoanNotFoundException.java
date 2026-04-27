package com.booklovers.book_lovers_project.exception;

public class LoanNotFoundException extends RuntimeException {
	public LoanNotFoundException(String message) {
		super(message);
	}
}