package com.booklovers.book_lovers_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.book_lovers_project.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
	Optional<CategoryEntity> findByNameIgnoreCase(String name);

	boolean existsByNameIgnoreCase(String name);
}