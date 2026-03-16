package com.booklovers.book_lovers_project.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
	@NotBlank
	@Size(min = 3, max = 50)
	private String username;

	@NotBlank(message = "Email boş qoyula bilməz")
	@Email
	private String email;

	@NotBlank(message = "Şifrə boş qoyula bilməz")
	@Size(min = 6, max = 100)
	private String password;

	// --- Manual Getter və Setter metodları ---

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}