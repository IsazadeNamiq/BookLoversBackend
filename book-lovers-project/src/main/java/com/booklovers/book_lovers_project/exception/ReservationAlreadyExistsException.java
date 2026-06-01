package com.booklovers.book_lovers_project.exception;

public class ReservationAlreadyExistsException extends RuntimeException {
	public ReservationAlreadyExistsException(String message) {
		super(message);
	}
}