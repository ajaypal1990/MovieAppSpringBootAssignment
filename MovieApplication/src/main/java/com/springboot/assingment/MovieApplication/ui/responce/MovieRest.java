package com.springboot.assingment.MovieApplication.ui.responce;

public class MovieRest {

	private String movieId;
	private String title;
	private String category;
	private Long starRating;
	
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
