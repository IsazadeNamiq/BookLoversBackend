package com.booklovers.book_lovers_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booklovers.book_lovers_project.dto.BookDto;
import com.booklovers.book_lovers_project.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto) {
		BookDto created = bookService.create(bookDto);
		return ResponseEntity.status(201).body(created);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getBook(@PathVariable Integer id) {
		BookDto dto = bookService.getById(id);
		return ResponseEntity.ok(dto);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BookDto> updateBook(@PathVariable Integer id, @Valid @RequestBody BookDto bookDto) {
		BookDto updated = bookService.update(id, bookDto);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<Page<BookDto>> listBooks(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "sort", defaultValue = "id,desc") String sort) {
		Sort springSort = parseSort(sort);
		Pageable pageable = PageRequest.of(page, size, springSort);
		Page<BookDto> result = bookService.getAll(search, pageable);
		return ResponseEntity.ok(result);
	}

	private Sort parseSort(String sort) {
		try {
			String[] parts = sort.split(",");
			String prop = parts[0];
			Sort.Direction dir = parts.length > 1 && parts[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC
					: Sort.Direction.DESC;
			return Sort.by(dir, prop);
		} catch (Exception e) {
			return Sort.by(Sort.Direction.DESC, "id");
		}
	}
}