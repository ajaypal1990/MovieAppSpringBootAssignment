package com.springboot.assingment.MovieApplication.io.entity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRespository extends PagingAndSortingRepository<MovieEntity, Long> {

	MovieEntity findByTitle(String title);

	MovieEntity findByMovieId(String id);

	void deleteByMovieId(String movieId);

}
