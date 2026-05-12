package com.booklovers.book_lovers_project.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	String saveBookCover(MultipartFile file);

	void deleteFile(String filePath);
}