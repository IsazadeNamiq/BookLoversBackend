package com.booklovers.book_lovers_project.exception;

public class BookUnavailableException extends RuntimeException {
	public BookUnavailableException(String message) {
		super(message);
	}
}