package com.booklovers.book_lovers_project.service;

import com.booklovers.book_lovers_project.dto.AuthRequest;
import com.booklovers.book_lovers_project.dto.AuthResponse;
import com.booklovers.book_lovers_project.dto.RegisterRequest;

public interface AuthService {
	AuthResponse login(AuthRequest request);

	void register(RegisterRequest request);
}