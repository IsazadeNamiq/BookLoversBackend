package com.booklovers.book_lovers_project.exception;

public class FavoriteNotFoundException extends RuntimeException {
	public FavoriteNotFoundException(String message) {
		super(message);
	}
}