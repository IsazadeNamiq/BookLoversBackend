package com.booklovers.book_lovers_project.service;

import com.booklovers.book_lovers_project.request.AuthRequest;
import com.booklovers.book_lovers_project.request.RegisterRequest;
import com.booklovers.book_lovers_project.response.AuthResponse;

public interface AuthService {
	AuthResponse login(AuthRequest request);

	void register(RegisterRequest request);
}