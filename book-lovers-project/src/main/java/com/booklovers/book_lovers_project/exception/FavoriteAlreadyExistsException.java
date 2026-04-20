package com.booklovers.book_lovers_project.exception;

public class FavoriteAlreadyExistsException extends RuntimeException {
	public FavoriteAlreadyExistsException(String message) {
		super(message);
	}
}