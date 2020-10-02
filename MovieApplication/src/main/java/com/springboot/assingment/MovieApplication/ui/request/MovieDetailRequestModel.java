package com.springboot.assingment.MovieApplication.ui.request;

public class MovieDetailRequestModel {

	private String title;
	private String category;
	private Long starRating;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getStarRating() {
		return starRating;
	}
	public void setStarRating(Long starRating) {
		this.starRating = starRating;
	}
	
	
}
