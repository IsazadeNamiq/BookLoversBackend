package com.booklovers.book_lovers_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.book_lovers_project.entity.LoanEntity;

public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {

	List<LoanEntity> findByUser_UsernameOrderByBorrowedAtDesc(String username);

	Optional<LoanEntity> findByBook_IdAndUser_UsernameAndReturnedAtIsNull(Integer bookId, String username);

	long countByStatus(com.booklovers.book_lovers_project.entity.LoanStatus status);

}