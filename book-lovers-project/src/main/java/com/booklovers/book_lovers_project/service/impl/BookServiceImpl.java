package com.booklovers.book_lovers_project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.booklovers.book_lovers_project.dto.BookDto;
import com.booklovers.book_lovers_project.entity.BookEntity;
import com.booklovers.book_lovers_project.exception.ResourceNotFoundException;
import com.booklovers.book_lovers_project.repository.BookRepository;
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
	public BookDto create(BookDto bookDto) {
		BookEntity entity = modelMapper.map(bookDto, BookEntity.class);
		BookEntity saved = bookRepository.save(entity);
		return modelMapper.map(saved, BookDto.class);
	}

	@Override
	public BookDto update(Integer id, BookDto bookDto) {
		BookEntity existingBook = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
		// sahələri yenilə
		existingBook.setTitle(bookDto.getTitle());
		existingBook.setDescription(bookDto.getDescription());
		existingBook.setAuthor(bookDto.getAuthor());
		existingBook.setIsbn(bookDto.getIsbn());
		existingBook.setPages(bookDto.getPages());
		existingBook.setPublishedDate(bookDto.getPublishedDate());
		existingBook.setCoverImagePath(bookDto.getCoverImagePath());
		existingBook.setPrice(bookDto.getPrice());

		BookEntity updatedBook = bookRepository.save(existingBook);
		return modelMapper.map(updatedBook, BookDto.class);
	}

	@Override
	public BookDto getById(Integer id) {
		BookEntity entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
		return modelMapper.map(entity, BookDto.class);
	}

	@Override
	public void delete(Integer id) {
		BookEntity entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
		bookRepository.delete(entity);
	}

	@Override
	public Page<BookDto> getAll(String search, Pageable pageable) {
		Page<BookEntity> page;
		if (search == null || search.isBlank()) {
			page = bookRepository.findAll(pageable);
		} else {
			page = bookRepository.findByTitleOrAuthor(search, search, pageable);
		}
		return page.map(entity -> modelMapper.map(entity, BookDto.class));
	}
}