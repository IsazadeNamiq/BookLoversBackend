package com.booklovers.book_lovers_project.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.booklovers.book_lovers_project.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	private final Path uploadDir;

	public FileStorageServiceImpl(@Value("${app.upload.dir}") String uploadDir) {
		this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.uploadDir);
		} catch (IOException e) {
			throw new RuntimeException("Upload qovluğu yaradıla bilmədi: " + this.uploadDir, e);
		}
	}

	@Override
	public String saveBookCover(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new RuntimeException("Fayl boşdur");
		}

		String originalName = file.getOriginalFilename();
		String extension = "";

		if (originalName != null && originalName.contains(".")) {
			extension = originalName.substring(originalName.lastIndexOf("."));
		}

		String newFileName = UUID.randomUUID() + extension;
		Path target = uploadDir.resolve(newFileName);

		try {
			Files.copy(file.getInputStream(), target);
			return "uploads/" + newFileName;
		} catch (IOException e) {
			throw new RuntimeException("Fayl saxlanıla bilmədi", e);
		}
	}

	@Override
	public void deleteFile(String filePath) {
		if (filePath == null || filePath.isBlank()) {
			return;
		}

		try {
			Path path = Paths.get(filePath.replace("uploads/", "")).isAbsolute() ? Paths.get(filePath)
					: uploadDir.resolve(Paths.get(filePath).getFileName());

			Files.deleteIfExists(path);
		} catch (Exception ignored) {
		}
	}
}