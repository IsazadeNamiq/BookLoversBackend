package com.booklovers.book_lovers_project.exception;

public class ReservationNotReadyException extends RuntimeException {
	public ReservationNotReadyException(String message) {
		super(message);
	}
}