package com.booklovers.book_lovers_project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.booklovers.book_lovers_project.dto.BookDto;

public interface BookService {
	BookDto create(BookDto bookDto);

	BookDto update(Integer id, BookDto bookDto);

	BookDto getById(Integer id);

	void delete(Integer id);

	Page<BookDto> getAll(String search, Pageable pageable);
}