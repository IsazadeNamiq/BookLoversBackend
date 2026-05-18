package com.booklovers.book_lovers_project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.booklovers.book_lovers_project.entity.BookEntity;
import com.booklovers.book_lovers_project.exception.ResourceNotFoundException;
import com.booklovers.book_lovers_project.repository.BookRepository;
import com.booklovers.book_lovers_project.request.BookFilterRequest;
import com.booklovers.book_lovers_project.request.BookRequest;
import com.booklovers.book_lovers_project.response.BookResponse;
import com.booklovers.book_lovers_project.service.BookService;
import com.booklovers.book_lovers_project.service.FileStorageService;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final ModelMapper modelMapper;
	private final FileStorageService fileStorageService;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper,
			FileStorageService fileStorageService) {
		this.bookRepository = bookRepository;
		this.modelMapper = modelMapper;
		this.fileStorageService = fileStorageService;
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

	@Override
	public String updateCover(Integer id, MultipartFile file) {
		BookEntity book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

		String oldPath = book.getCoverImagePath();
		String newPath = fileStorageService.saveBookCover(file);

		book.setCoverImagePath(newPath);
		bookRepository.save(book);

		if (oldPath != null && !oldPath.isBlank()) {
			fileStorageService.deleteFile(oldPath);
		}

		return newPath;
	}

	@Override
	public Page<BookResponse> searchBooks(BookFilterRequest filterRequest) {
		List<BookEntity> books = bookRepository.findAll();
		List<BookEntity> filteredBooks = new ArrayList<>(books);

		if (filterRequest.getKeyword() != null && !filterRequest.getKeyword().isBlank()) {
			filteredBooks = filteredBooks.stream().filter(book -> (book.getTitle() != null
					&& book.getTitle().toLowerCase().contains(filterRequest.getKeyword().toLowerCase()))
					|| (book.getAuthor() != null
							&& book.getAuthor().toLowerCase().contains(filterRequest.getKeyword().toLowerCase())))
					.toList();
		}

		if (filterRequest.getAuthor() != null && !filterRequest.getAuthor().isBlank()) {
			filteredBooks = filteredBooks.stream()
					.filter(book -> book.getAuthor() != null
							&& book.getAuthor().toLowerCase().contains(filterRequest.getAuthor().toLowerCase()))
					.toList();
		}

		if (filterRequest.getCategoryId() != null) {
			filteredBooks = filteredBooks.stream().filter(book -> book.getCategory() != null
					&& book.getCategory().getId().equals(filterRequest.getCategoryId())).toList();
		}

		if (filterRequest.getMinPrice() != null) {
			filteredBooks = filteredBooks.stream()
					.filter(book -> book.getPrice() != null && book.getPrice() >= filterRequest.getMinPrice()).toList();
		}

		if (filterRequest.getMaxPrice() != null) {
			filteredBooks = filteredBooks.stream()
					.filter(book -> book.getPrice() != null && book.getPrice() <= filterRequest.getMaxPrice()).toList();
		}

		if (Boolean.TRUE.equals(filterRequest.getAvailableOnly())) {
			filteredBooks = filteredBooks.stream()
					.filter(book -> book.getAvailableCopies() != null && book.getAvailableCopies() > 0).toList();
		}

		return (Page<BookResponse>) filteredBooks.stream().map(book -> modelMapper.map(book, BookResponse.class))
				.toList();
	}

}