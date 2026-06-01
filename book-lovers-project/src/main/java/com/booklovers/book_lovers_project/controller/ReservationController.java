package com.booklovers.book_lovers_project.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booklovers.book_lovers_project.response.ReservationResponse;
import com.booklovers.book_lovers_project.service.ReservationService;

@RestController
@RequestMapping("/api")
public class ReservationController {

	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@PostMapping("/books/{bookId}/reserve")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<ReservationResponse> reserveBook(@PathVariable Integer bookId, Principal principal) {
		return ResponseEntity.ok(reservationService.reserveBook(bookId, principal.getName()));
	}

	@DeleteMapping("/reservations/{reservationId}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Void> cancelReservation(@PathVariable Integer reservationId, Authentication authentication) {
		boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

		reservationService.cancelReservation(reservationId, authentication.getName(), isAdmin);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/users/me/reservations")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<ReservationResponse>> myReservations(Principal principal) {
		return ResponseEntity.ok(reservationService.getMyReservations(principal.getName()));
	}

	@GetMapping("/books/{bookId}/reservations")
	public ResponseEntity<List<ReservationResponse>> reservationsForBook(@PathVariable Integer bookId) {
		return ResponseEntity.ok(reservationService.getReservationsForBook(bookId));
	}

	@GetMapping("/books/{bookId}/reservations/count")
	public ResponseEntity<Map<String, Object>> countWaiting(@PathVariable Integer bookId) {
		return ResponseEntity.ok(
				Map.of("bookId", bookId, "waitingReservations", reservationService.countWaitingReservations(bookId)));
	}
}