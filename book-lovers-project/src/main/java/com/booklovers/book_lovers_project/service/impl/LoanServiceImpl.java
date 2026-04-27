package com.booklovers.book_lovers_project.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.booklovers.book_lovers_project.entity.BookEntity;
import com.booklovers.book_lovers_project.entity.LoanEntity;
import com.booklovers.book_lovers_project.entity.LoanStatus;
import com.booklovers.book_lovers_project.entity.UserEntity;
import com.booklovers.book_lovers_project.exception.BookUnavailableException;
import com.booklovers.book_lovers_project.exception.LoanAlreadyReturnedException;
import com.booklovers.book_lovers_project.exception.LoanNotFoundException;
import com.booklovers.book_lovers_project.exception.ResourceNotFoundException;
import com.booklovers.book_lovers_project.repository.BookRepository;
import com.booklovers.book_lovers_project.repository.LoanRepository;
import com.booklovers.book_lovers_project.repository.UserRepository;
import com.booklovers.book_lovers_project.response.LoanResponse;
import com.booklovers.book_lovers_project.service.LoanService;

@Service
public class LoanServiceImpl implements LoanService {

	private final LoanRepository loanRepository;
	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository,
			UserRepository userRepository) {
		this.loanRepository = loanRepository;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}

	@Override
	public LoanResponse borrowBook(Integer bookId, String username, Integer days) {
		BookEntity book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

		Integer available = book.getAvailableCopies();
		if (available == null || available <= 0) {
			throw new BookUnavailableException("Bu kitabdan hazırda yoxdur");
		}

		loanRepository.findByBook_IdAndUser_UsernameAndReturnedAtIsNull(bookId, username).ifPresent(existing -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bu kitabı artıq borca götürmüsən");
		});

		book.setAvailableCopies(available - 1);
		bookRepository.save(book);

		LoanEntity loan = new LoanEntity();
		loan.setBook(book);
		loan.setUser(user);
		loan.setBorrowedAt(LocalDateTime.now());
		loan.setDueDate(LocalDateTime.now().plusDays(days == null || days <= 0 ? 14 : days));
		loan.setStatus(LoanStatus.BORROWED);

		return toResponse(loanRepository.save(loan));
	}

	@Override
	public LoanResponse returnBook(Integer loanId, String username, boolean isAdmin) {
		LoanEntity loan = loanRepository.findById(loanId)
				.orElseThrow(() -> new LoanNotFoundException("Loan tapılmadı: " + loanId));

		if (!isAdmin && !loan.getUser().getUsername().equals(username)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bu borcu qaytarmaq icazən yoxdur");
		}

		if (loan.getStatus() == LoanStatus.RETURNED) {
			throw new LoanAlreadyReturnedException("Bu kitab artıq qaytarılıb");
		}

		loan.setReturnedAt(LocalDateTime.now());
		loan.setStatus(LoanStatus.RETURNED);

		BookEntity book = loan.getBook();
		Integer available = book.getAvailableCopies();
		book.setAvailableCopies((available == null ? 0 : available) + 1);
		bookRepository.save(book);

		return toResponse(loanRepository.save(loan));
	}

	@Override
	public List<LoanResponse> getMyLoans(String username) {
		return loanRepository.findByUser_UsernameOrderByBorrowedAtDesc(username).stream().map(this::toResponse)
				.collect(Collectors.toList());
	}

	private LoanResponse toResponse(LoanEntity loan) {
		LoanResponse response = new LoanResponse();
		response.setId(loan.getId());
		response.setBookId(loan.getBook().getId());
		response.setBookTitle(loan.getBook().getTitle());
		response.setUserId(loan.getUser().getId());
		response.setUsername(loan.getUser().getUsername());
		response.setBorrowedAt(loan.getBorrowedAt());
		response.setDueDate(loan.getDueDate());
		response.setReturnedAt(loan.getReturnedAt());
		response.setStatus(loan.getStatus());
		return response;
	}
}