package com.booklovers.book_lovers_project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.booklovers.book_lovers_project.entity.BookEntity;
import com.booklovers.book_lovers_project.entity.ReservationEntity;
import com.booklovers.book_lovers_project.entity.ReservationStatus;
import com.booklovers.book_lovers_project.entity.UserEntity;
import com.booklovers.book_lovers_project.exception.ReservationAlreadyExistsException;
import com.booklovers.book_lovers_project.exception.ReservationNotFoundException;
import com.booklovers.book_lovers_project.exception.ResourceNotFoundException;
import com.booklovers.book_lovers_project.repository.BookRepository;
import com.booklovers.book_lovers_project.repository.ReservationRepository;
import com.booklovers.book_lovers_project.repository.UserRepository;
import com.booklovers.book_lovers_project.response.ReservationResponse;
import com.booklovers.book_lovers_project.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;
	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	public ReservationServiceImpl(ReservationRepository reservationRepository, BookRepository bookRepository,
			UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}

	@Override
	public ReservationResponse reserveBook(Integer bookId, String username) {
		BookEntity book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

		reservationRepository.findByBook_IdAndUser_UsernameAndStatus(bookId, username, ReservationStatus.WAITING)
				.ifPresent(r -> {
					throw new ReservationAlreadyExistsException("Bu kitab üçün aktiv rezervasiyan var");
				});

		Integer available = book.getAvailableCopies();
		if (available != null && available > 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kitab mövcuddur, rezervasiya lazım deyil");
		}

		ReservationEntity reservation = new ReservationEntity();
		reservation.setBook(book);
		reservation.setUser(user);
		reservation.setStatus(ReservationStatus.WAITING);

		return toResponse(reservationRepository.save(reservation));
	}

	@Override
	public void cancelReservation(Integer reservationId, String username, boolean isAdmin) {
		ReservationEntity reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation not found: " + reservationId));

		if (!isAdmin && !reservation.getUser().getUsername().equals(username)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bu rezervasiyanı ləğv etmək icazən yoxdur");
		}

		if (reservation.getStatus() != ReservationStatus.WAITING) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Yalnız gözləyən rezervasiya ləğv edilə bilər");
		}

		reservation.setStatus(ReservationStatus.CANCELLED);
		reservationRepository.save(reservation);
	}

	@Override
	public List<ReservationResponse> getMyReservations(String username) {
		return reservationRepository.findByUser_UsernameOrderByCreatedAtDesc(username).stream().map(this::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public List<ReservationResponse> getReservationsForBook(Integer bookId) {
		return reservationRepository.findByBook_IdOrderByCreatedAtAsc(bookId).stream().map(this::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public long countWaitingReservations(Integer bookId) {
		return reservationRepository.countByBook_IdAndStatus(bookId, ReservationStatus.WAITING);
	}

	private ReservationResponse toResponse(ReservationEntity reservation) {
		ReservationResponse response = new ReservationResponse();
		response.setId(reservation.getId());
		response.setBookId(reservation.getBook().getId());
		response.setBookTitle(reservation.getBook().getTitle());
		response.setUserId(reservation.getUser().getId());
		response.setUsername(reservation.getUser().getUsername());
		response.setStatus(reservation.getStatus());
		response.setCreatedAt(reservation.getCreatedAt());
		return response;
	}
}