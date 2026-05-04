package com.booklovers.book_lovers_project.service;

import java.util.List;

import com.booklovers.book_lovers_project.request.CategoryRequest;
import com.booklovers.book_lovers_project.response.CategoryResponse;

public interface CategoryService {
	CategoryResponse create(CategoryRequest request);

	CategoryResponse update(Integer id, CategoryRequest request);

	CategoryResponse getById(Integer id);

	void delete(Integer id);

	List<CategoryResponse> getAll();
}