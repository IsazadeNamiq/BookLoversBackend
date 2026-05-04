package com.booklovers.book_lovers_project.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponse {

	private Integer id;
	private String name;
	private String description;
	private Long bookCount;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getBookCount() {
		return this.bookCount;
	}

	public void setBookCount(Long bookCount) {
		this.bookCount = bookCount;
	}
}