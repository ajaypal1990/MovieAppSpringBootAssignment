package com.springboot.assingment.MovieApplication.service;

import java.util.List;

import com.springboot.assingment.MovieApplication.ui.request.MovieDetailRequestModel;
import com.springboot.assingment.MovieApplication.ui.shared.dto.MovieDto;

public interface MovieService {

	MovieDto createMovie(MovieDto movieDto);

	MovieDto getMovieByMovieId(String id);

	MovieDto updateMovie(String id, MovieDetailRequestModel moviedetails);

	void deleteMovie(String id);

	List<MovieDto> getMovies(int page, int limit);


}
