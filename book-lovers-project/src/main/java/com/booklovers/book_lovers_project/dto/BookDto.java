package com.booklovers.book_lovers_project.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDto {
	private int id;

	@NotBlank(message = "Başlıq boş ola bilməz")
	@Size(max = 255)
	private String title;

	@Size(max = 2000)
	private String description;

	@Size(max = 255)
	private String author;

	@Size(min = 10, max = 13)
	private String isbn;

	@Min(value = 0, message = "Səhifələr mənfi ola bilməz")
	private Integer pages;

	@Past(message = "tarix keçmiş zamanda olmalıdır")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate publishedDate;

	private String coverImagePath;
}