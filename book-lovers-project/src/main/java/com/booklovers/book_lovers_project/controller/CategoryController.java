package com.booklovers.book_lovers_project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booklovers.book_lovers_project.request.CategoryRequest;
import com.booklovers.book_lovers_project.response.CategoryResponse;
import com.booklovers.book_lovers_project.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
		return ResponseEntity.ok(categoryService.create(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryResponse> update(@PathVariable Integer id,
			@Valid @RequestBody CategoryRequest request) {
		return ResponseEntity.ok(categoryService.update(id, request));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(categoryService.getById(id));
	}

	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAll() {
		return ResponseEntity.ok(categoryService.getAll());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}
}