package com.booklovers.book_lovers_project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.booklovers.book_lovers_project.entity.BookEntity;
import com.booklovers.book_lovers_project.entity.FavoriteEntity;
import com.booklovers.book_lovers_project.entity.UserEntity;
import com.booklovers.book_lovers_project.exception.FavoriteAlreadyExistsException;
import com.booklovers.book_lovers_project.exception.FavoriteNotFoundException;
import com.booklovers.book_lovers_project.exception.ResourceNotFoundException;
import com.booklovers.book_lovers_project.repository.BookRepository;
import com.booklovers.book_lovers_project.repository.FavoriteRepository;
import com.booklovers.book_lovers_project.repository.UserRepository;
import com.booklovers.book_lovers_project.response.FavoriteResponse;
import com.booklovers.book_lovers_project.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	private final FavoriteRepository favoriteRepository;
	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	public FavoriteServiceImpl(FavoriteRepository favoriteRepository, BookRepository bookRepository,
			UserRepository userRepository) {
		this.favoriteRepository = favoriteRepository;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}

	@Override
	public FavoriteResponse addFavorite(Integer bookId, String username) {
		BookEntity book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

		favoriteRepository.findByUser_UsernameAndBook_Id(username, bookId).ifPresent(f -> {
			throw new FavoriteAlreadyExistsException("Bu kitab artıq favorilərdədir");
		});

		FavoriteEntity favorite = new FavoriteEntity();
		favorite.setBook(book);
		favorite.setUser(user);

		return toResponse(favoriteRepository.save(favorite));
	}

	@Override
	public void removeFavorite(Integer bookId, String username) {
		FavoriteEntity favorite = favoriteRepository.findByUser_UsernameAndBook_Id(username, bookId)
				.orElseThrow(() -> new FavoriteNotFoundException("Favorite not found"));

		favoriteRepository.delete(favorite);
	}

	@Override
	public List<FavoriteResponse> getMyFavorites(String username) {
		return favoriteRepository.findByUser_UsernameOrderByCreatedAtDesc(username).stream().map(this::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public long countFavorites(Integer bookId) {
		return favoriteRepository.countByBook_Id(bookId);
	}

	private FavoriteResponse toResponse(FavoriteEntity favorite) {
		FavoriteResponse response = new FavoriteResponse();
		response.setId(favorite.getId());
		response.setBookId(favorite.getBook().getId());
		response.setBookTitle(favorite.getBook().getTitle());
		response.setBookAuthor(favorite.getBook().getAuthor());
		response.setUserId(favorite.getUser().getId());
		response.setUsername(favorite.getUser().getUsername());
		response.setCreatedAt(favorite.getCreatedAt());
		return response;
	}
}