package com.booklovers.book_lovers_project.service;

import java.util.List;

import com.booklovers.book_lovers_project.response.ReservationResponse;

public interface ReservationService {
	ReservationResponse reserveBook(Integer bookId, String username);

	void cancelReservation(Integer reservationId, String username, boolean isAdmin);

	List<ReservationResponse> getMyReservations(String username);

	List<ReservationResponse> getReservationsForBook(Integer bookId);

	long countWaitingReservations(Integer bookId);
}