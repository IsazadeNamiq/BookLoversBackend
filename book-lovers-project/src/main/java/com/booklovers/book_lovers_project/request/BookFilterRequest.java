package com.booklovers.book_lovers_project.request;

public class BookFilterRequest {

	private String keyword;
	private String author;
	private Integer categoryId;
	private Double minPrice;
	private Double maxPrice;
	private Boolean availableOnly;

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Double getMinPrice() {
		return this.minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Boolean getAvailableOnly() {
		return this.availableOnly;
	}

	public void setAvailableOnly(Boolean availableOnly) {
		this.availableOnly = availableOnly;
	}
}