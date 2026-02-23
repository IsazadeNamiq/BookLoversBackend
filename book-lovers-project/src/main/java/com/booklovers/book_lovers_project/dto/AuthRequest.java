package com.booklovers.book_lovers_project.dto;

import lombok.Data;

@Data
public class AuthRequest {
	private String username;
	private String password;
}