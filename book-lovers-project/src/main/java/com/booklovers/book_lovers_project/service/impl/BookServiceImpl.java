package com.booklovers.book_lovers_project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.booklovers.book_lovers_project.entity.BookEntity;
import com.booklovers.book_lovers_project.exception.ResourceNotFoundException;
import com.booklovers.book_lovers_project.repository.BookRepository;
import com.booklovers.book_lovers_project.request.BookRequest;
import com.booklovers.book_lovers_project.response.BookResponse;
import com.booklovers.book_lovers_project.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
		this.bookRepository = bookRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public BookResponse create(BookRequest bookRequest) {
		BookEntity entity = modelMapper.map(bookRequest, BookEntity.class);
		BookEntity saved = bookRepository.save(entity);
		return modelMapper.map(saved, BookResponse.class);
	}

	@Override
	public BookResponse update(int id, BookRequest bookRequest) {
		BookEntity existing = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

		existing.setTitle(bookRequest.getTitle());
		existing.setDescription(bookRequest.getDescription());
		existing.setAuthor(bookRequest.getAuthor());
		existing.setIsbn(bookRequest.getIsbn());
		existing.setPages(bookRequest.getPages());
		existing.setPublishedDate(bookRequest.getPublishedDate());
		existing.setCoverImagePath(bookRequest.getCoverImagePath());
		existing.setPrice(bookRequest.getPrice());

		BookEntity updated = bookRepository.save(existing);
		return modelMapper.map(updated, BookResponse.class);
	}

	@Override
	public BookResponse getById(int id) {
		BookEntity entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
		return modelMapper.map(entity, BookResponse.class);
	}

	@Override
	public void delete(int id) {
		BookEntity entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
		bookRepository.delete(entity);
	}

	@Override
	public Page<BookResponse> getAll(String search, Pageable pageable) {
		Page<BookEntity> page;
		if (search == null || search.isBlank()) {
			page = bookRepository.findAll(pageable);
		} else {
			page = bookRepository.findByTitleOrAuthor(search, search, pageable);
		}
		return page.map(entity -> modelMapper.map(entity, BookResponse.class));
	}
}