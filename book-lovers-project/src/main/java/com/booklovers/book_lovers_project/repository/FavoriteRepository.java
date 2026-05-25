package com.booklovers.book_lovers_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.book_lovers_project.entity.FavoriteEntity;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

	Optional<FavoriteEntity> findByUser_UsernameAndBook_Id(String username, Integer bookId);

	List<FavoriteEntity> findByUser_UsernameOrderByCreatedAtDesc(String username);

	long countByBook_Id(Integer bookId);

	List<FavoriteEntity> findTop5ByOrderByCreatedAtDesc();
}