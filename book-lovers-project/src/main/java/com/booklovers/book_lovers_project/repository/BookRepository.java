package com.booklovers.book_lovers_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.book_lovers_project.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
	Page<BookEntity> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author,
			Pageable pageable);
}