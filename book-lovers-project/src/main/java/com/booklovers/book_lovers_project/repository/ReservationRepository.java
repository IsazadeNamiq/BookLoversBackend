package com.booklovers.book_lovers_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.book_lovers_project.entity.ReservationEntity;
import com.booklovers.book_lovers_project.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

	List<ReservationEntity> findByUser_UsernameOrderByCreatedAtDesc(String username);

	List<ReservationEntity> findByBook_IdOrderByCreatedAtAsc(Integer bookId);

	Optional<ReservationEntity> findByBook_IdAndUser_UsernameAndStatus(Integer bookId, String username,
			ReservationStatus status);

	long countByBook_IdAndStatus(Integer bookId, ReservationStatus status);
}