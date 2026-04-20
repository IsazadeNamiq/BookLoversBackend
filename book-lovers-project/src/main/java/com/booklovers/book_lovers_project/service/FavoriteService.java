package com.booklovers.book_lovers_project.service;

import java.util.List;

import com.booklovers.book_lovers_project.response.FavoriteResponse;

public interface FavoriteService {
	FavoriteResponse addFavorite(Integer bookId, String username);

	void removeFavorite(Integer bookId, String username);

	List<FavoriteResponse> getMyFavorites(String username);

	long countFavorites(Integer bookId);
}