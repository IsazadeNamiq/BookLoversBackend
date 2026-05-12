package com.booklovers.book_lovers_project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.booklovers.book_lovers_project.request.BookRequest;
import com.booklovers.book_lovers_project.response.BookResponse;

public interface BookService {
	BookResponse create(BookRequest bookRequest);

	BookResponse update(int id, BookRequest bookRequest);

	BookResponse getById(int id);

	void delete(int id);

	String updateCover(Integer id, MultipartFile file);

	Page<BookResponse> getAll(String search, Pageable pageable);
}