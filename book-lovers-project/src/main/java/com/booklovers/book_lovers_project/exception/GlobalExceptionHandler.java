package com.booklovers.book_lovers_project.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Strukturlaşdırılmış API error
	public static class ApiError {
		public LocalDateTime timestamp;
		public int status;
		public String error;
		public String path;
		public Object details;

		public ApiError(LocalDateTime timestamp, int status, String error, String path, Object details) {
			this.timestamp = timestamp;
			this.status = status;
			this.error = error;
			this.path = path;
			this.details = details;
		}
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				req.getRequestURI(), null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
		Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a + ";" + b));
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Validation failed",
				req.getRequestURI(), fields);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleOther(Exception ex, HttpServletRequest req) {
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
				req.getRequestURI(), null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}
}