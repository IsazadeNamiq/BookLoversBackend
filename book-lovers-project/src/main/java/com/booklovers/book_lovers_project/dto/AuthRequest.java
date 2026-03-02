package com.booklovers.book_lovers_project.dto;

import lombok.Data;

@Data
public class AuthRequest {
	private String username;
	private String password;

	// --- Manual Getter və Setter metodları ---

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}