package com.booklovers.book_lovers_project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booklovers.book_lovers_project.response.LoanResponse;
import com.booklovers.book_lovers_project.service.LoanService;

@RestController
@RequestMapping("/api")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@PostMapping("/books/{bookId}/borrow")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<LoanResponse> borrowBook(@PathVariable Integer bookId,
			@RequestParam(defaultValue = "14") Integer days, Principal principal) {
		return ResponseEntity.ok(loanService.borrowBook(bookId, principal.getName(), days));
	}

	@PostMapping("/loans/{loanId}/return")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<LoanResponse> returnBook(@PathVariable Integer loanId, Authentication authentication) {
		boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

		return ResponseEntity.ok(loanService.returnBook(loanId, authentication.getName(), isAdmin));
	}

	@GetMapping("/users/me/loans")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<LoanResponse>> myLoans(Principal principal) {
		return ResponseEntity.ok(loanService.getMyLoans(principal.getName()));
	}
}