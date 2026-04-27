package com.booklovers.book_lovers_project.service;

import java.util.List;

import com.booklovers.book_lovers_project.response.LoanResponse;

public interface LoanService {
	LoanResponse borrowBook(Integer bookId, String username, Integer days);

	LoanResponse returnBook(Integer loanId, String username, boolean isAdmin);

	List<LoanResponse> getMyLoans(String username);
}