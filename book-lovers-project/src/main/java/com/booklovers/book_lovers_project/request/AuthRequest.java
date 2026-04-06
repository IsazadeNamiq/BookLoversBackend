package com.booklovers.book_lovers_project.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
	@NotBlank(message = "İstifadəçi adı boş ola bilməz")
	private String username;

	@NotBlank(message = "Şifrə boş ola bilməz")
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