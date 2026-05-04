package com.booklovers.book_lovers_project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.booklovers.book_lovers_project.entity.CategoryEntity;
import com.booklovers.book_lovers_project.exception.ResourceNotFoundException;
import com.booklovers.book_lovers_project.repository.BookRepository;
import com.booklovers.book_lovers_project.repository.CategoryRepository;
import com.booklovers.book_lovers_project.request.CategoryRequest;
import com.booklovers.book_lovers_project.response.CategoryResponse;
import com.booklovers.book_lovers_project.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final BookRepository bookRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository, BookRepository bookRepository) {
		this.categoryRepository = categoryRepository;
		this.bookRepository = bookRepository;
	}

	@Override
	public CategoryResponse create(CategoryRequest request) {
		if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
			throw new RuntimeException("Bu adda kateqoriya artıq var");
		}

		CategoryEntity category = new CategoryEntity();
		category.setName(request.getName());
		category.setDescription(request.getDescription());

		return toResponse(categoryRepository.save(category));
	}

	@Override
	public CategoryResponse update(Integer id, CategoryRequest request) {
		CategoryEntity category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

		category.setName(request.getName());
		category.setDescription(request.getDescription());

		return toResponse(categoryRepository.save(category));
	}

	@Override
	public CategoryResponse getById(Integer id) {
		CategoryEntity category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
		return toResponse(category);
	}

	@Override
	public void delete(Integer id) {
		CategoryEntity category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
		categoryRepository.delete(category);
	}

	@Override
	public List<CategoryResponse> getAll() {
		return categoryRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	private CategoryResponse toResponse(CategoryEntity category) {
		CategoryResponse response = new CategoryResponse();
		response.setId(category.getId());
		response.setName(category.getName());
		response.setDescription(category.getDescription());
		response.setBookCount(bookRepository.countByCategory_Id(category.getId()));
		return response;
	}
}