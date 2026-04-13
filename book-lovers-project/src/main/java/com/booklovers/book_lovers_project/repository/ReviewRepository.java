package com.booklovers.book_lovers_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booklovers.book_lovers_project.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

	List<ReviewEntity> findByBook_IdOrderByCreatedAtDesc(Integer bookId);

	Optional<ReviewEntity> findByIdAndUser_Username(Integer id, String username);

	Optional<ReviewEntity> findByBook_IdAndUser_Username(Integer bookId, String username);

	long countByBook_Id(Integer bookId);

	@Query("select avg(r.rating) from ReviewEntity r where r.book.id = :bookId")
	Double getAverageRatingByBookId(@Param("bookId") Integer bookId);
}