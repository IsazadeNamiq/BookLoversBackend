package com.booklovers.book_lovers_project.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class BookDto {
	private Integer id;

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

	@Min(value = 0, message = "Qiymət mənfi ola bilməz")
	private Double price;

	@Past(message = "tarix keçmiş zamanda olmalıdır")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate publishedDate;

	private String coverImagePath;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getPages() {
		return this.pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getPublishedDate() {
		return this.publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getCoverImagePath() {
		return this.coverImagePath;
	}

	public void setCoverImagePath(String coverImagePath) {
		this.coverImagePath = coverImagePath;
	}
}