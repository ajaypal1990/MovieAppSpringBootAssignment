package com.springboot.assingment.MovieApplication.ui.shared.dto;

public class MovieDto {
	private long id;
	private String movieId;
	private String title;
	private String category;
	private Long starRating;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
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
