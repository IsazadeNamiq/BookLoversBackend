package com.booklovers.book_lovers_project.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booklovers.book_lovers_project.response.FavoriteResponse;
import com.booklovers.book_lovers_project.service.FavoriteService;

@RestController
@RequestMapping("/api")
public class FavoriteController {

	private final FavoriteService favoriteService;

	public FavoriteController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	@PostMapping("/books/{bookId}/favorite")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<FavoriteResponse> addFavorite(@PathVariable Integer bookId, Principal principal) {
		return ResponseEntity.ok(favoriteService.addFavorite(bookId, principal.getName()));
	}

	@DeleteMapping("/books/{bookId}/favorite")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Void> removeFavorite(@PathVariable Integer bookId, Principal principal) {
		favoriteService.removeFavorite(bookId, principal.getName());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/users/me/favorites")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<FavoriteResponse>> myFavorites(Principal principal) {
		return ResponseEntity.ok(favoriteService.getMyFavorites(principal.getName()));
	}

	@GetMapping("/books/{bookId}/favorites/count")
	public ResponseEntity<Map<String, Object>> countFavorites(@PathVariable Integer bookId) {
		return ResponseEntity.ok(Map.of("bookId", bookId, "favoriteCount", favoriteService.countFavorites(bookId)));
	}
}