package com.booklovers.book_lovers_project.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.booklovers.book_lovers_project.request.BookRequest;
import com.booklovers.book_lovers_project.response.BookResponse;
import com.booklovers.book_lovers_project.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
//@SecurityRequirement(name = "bearerAuth")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@Operation(summary = "Kitab əlavə et")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request,
			UriComponentsBuilder uriBuilder) {
		BookResponse created = bookService.create(request);
		URI location = uriBuilder.path("/api/books/{id}").buildAndExpand(created.getId()).toUri();
		return ResponseEntity.created(location).body(created);
	}

	@Operation(summary = "Kitabı id-yə görə gətir")
	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(bookService.getById(id));
	}

	@Operation(summary = "Kitabı yenilə")
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BookResponse> update(@PathVariable Integer id, @Valid @RequestBody BookRequest request) {
		return ResponseEntity.ok(bookService.update(id, request));
	}

	@Operation(summary = "Kitabı sil")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}

//	@GetMapping
//	public ResponseEntity<Page<BookRequest>> listBooks(@RequestParam(value = "search", required = false) String search,
//			@RequestParam(value = "page", defaultValue = "0") Integer page,
//			@RequestParam(value = "size", defaultValue = "10") Integer size,
//			@RequestParam(value = "sort", defaultValue = "id,desc") String sort) {
//		Sort springSort = parseSort(sort);
//		Pageable pageable = PageRequest.of(page, size, springSort);
//		Page<BookResponse> result = bookService.getAll(search, pageable);
//		return ResponseEntity.ok(result);
//	}

	@Operation(summary = "Kitabları axtarış və səhifələmə ilə listələmə")
	@GetMapping
	public ResponseEntity<Page<BookResponse>> list(@RequestParam(required = false) String search, Pageable pageable) {
		return ResponseEntity.ok(bookService.getAll(search, pageable));
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