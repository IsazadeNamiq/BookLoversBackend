package com.booklovers.book_lovers_project.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequest {

	@Min(value = 1, message = "Reytinq minimum 1 olmalıdır")
	@Max(value = 5, message = "Reytinq maksimum 5 ola bilər")
	private Integer rating;

	@Size(max = 2000, message = "Şərh çox uzundur")
	private String comment;

	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}