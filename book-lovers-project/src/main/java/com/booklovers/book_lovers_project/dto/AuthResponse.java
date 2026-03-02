package com.booklovers.book_lovers_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class AuthResponse {
	private String token;

	public AuthResponse(String token) {
		this.token = token;
	} // --- Manual Getter və Setter metodları ---

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}