package com.booklovers.book_lovers_project.exception;

public class ReservationNotFoundException extends RuntimeException {
	public ReservationNotFoundException(String message) {
		super(message);
	}
}