package com.springboot.assingment.MovieApplication.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.assingment.MovieApplication.Exception.MovieServiceException;
import com.springboot.assingment.MovieApplication.io.entity.MovieEntity;
import com.springboot.assingment.MovieApplication.io.entity.MovieRespository;
import com.springboot.assingment.MovieApplication.service.impl.MovieServiceImpl;
import com.springboot.assingment.MovieApplication.ui.controller.MovieController;
import com.springboot.assingment.MovieApplication.ui.request.MovieDetailRequestModel;
import com.springboot.assingment.MovieApplication.ui.responce.ErrorMessages;
import com.springboot.assingment.MovieApplication.ui.responce.MovieRest;
import com.springboot.assingment.MovieApplication.ui.responce.OperationStatusModel;
import com.springboot.assingment.MovieApplication.ui.shared.Utils;
import com.springboot.assingment.MovieApplication.ui.shared.dto.MovieDto;

public class MovieControllerTest {
	@InjectMocks
	private MovieController movieController;
	@Mock
	private MovieServiceImpl movieServiceImpl;

	@Mock
	MovieRespository repository;

	@Mock
	Utils util;

	MovieDto movieDto;
	
	MovieEntity movieEntity ;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		movieDto = new 	MovieDto();
		movieDto.setId(1L);
		movieDto.setMovieId("y4ecu");
		movieDto.setTitle("Avenger");
		movieDto.setStarRating(3L);
		movieDto.setCategory("Adult");
	}
	
	@BeforeEach
	void setUpEntity() throws Exception {
		MockitoAnnotations.initMocks(this);
		movieEntity = new MovieEntity();
		movieEntity.setId(1L);
		movieEntity.setMovieId("y4ecu");
		movieEntity.setTitle("Avenger");
		movieEntity.setStarRating(3L);
		movieEntity.setCategory("Adult");
	}
	
	@Test
	void CreateMovieTest() {
		Mockito.when(repository.findByTitle(Mockito.anyString())).thenReturn(null);
		Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("dsfh");
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
		Mockito.when(movieServiceImpl.createMovie(Mockito.anyObject())).thenReturn(movieDto);
		Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(movieEntity);
		MovieRest movieRest =movieController.createMovie(getMovieDetailRequestModel());
		Assertions.assertNotNull(movieRest);
		Assertions.assertEquals("Avenger", movieRest.getTitle());
	}
	
		@Test
		void getMovieTestByMovieIdNull() {
		Mockito.when(repository.findByTitle(Mockito.anyString())).thenReturn(null);
		Mockito.when(movieServiceImpl.getMovieByMovieId(Mockito.anyString())).thenThrow( new MovieServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
		Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("dsfh");
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
		Mockito.when(movieServiceImpl.createMovie(Mockito.anyObject())).thenReturn(movieDto);
		Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(movieEntity);
		
		Exception exception = assertThrows(MovieServiceException.class, () -> {
			MovieRest movieRest =movieController.getMovie("y4ecu");
		});
	}
		
		@Test
		void getMovieTest() {
		Mockito.when(repository.findByTitle(Mockito.anyString())).thenReturn(null);
		Mockito.when(movieServiceImpl.getMovieByMovieId(Mockito.anyString())).thenReturn(movieDto);
		Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("dsfh");
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
		Mockito.when(movieServiceImpl.createMovie(Mockito.anyObject())).thenReturn(movieDto);
		Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(movieEntity);
		
			MovieRest movieRest =movieController.getMovie("y4ecu");
			Assertions.assertNotNull(movieRest);
			Assertions.assertEquals("Avenger", movieRest.getTitle());
	}
	
		@Test
		void updateMovieTest() {
			Mockito.when(repository.findByTitle(Mockito.anyString())).thenReturn(null);
			Mockito.when(movieServiceImpl.getMovieByMovieId(Mockito.anyString())).thenReturn(movieDto);
			Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("dsfh");
			Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
			Mockito.when(movieServiceImpl.updateMovie(Mockito.anyString(), Mockito.anyObject())).thenReturn(updateMovieDetail());
			Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(movieEntity);
			MovieRest movieRest =movieController.updateMovie("y4ecu", updateMovieDetailRequestModel());
			Assertions.assertNotNull(movieRest);
			Assertions.assertEquals("Updated Avenger", movieRest.getTitle());
		}
		
	private MovieDetailRequestModel getMovieDetailRequestModel() {
		MovieDetailRequestModel movie = new MovieDetailRequestModel();
		movie.setCategory("Adult");
		movie.setStarRating(3L);
		movie.setTitle("Avenger");

		return movie;
	}

	private MovieDetailRequestModel updateMovieDetailRequestModel() {
		MovieDetailRequestModel movie = new MovieDetailRequestModel();
		movie.setCategory("Adult");
		movie.setStarRating(3L);
		movie.setTitle("Updated Avenger");

		return movie;
	}
	
	private MovieDto updateMovieDetail() {
		MovieDto movieDto = new MovieDto();
		movieDto = new 	MovieDto();
		movieDto.setId(1L);
		movieDto.setMovieId("y4ecu");
		movieDto.setTitle("Updated Avenger");
		movieDto.setStarRating(3L);
		movieDto.setCategory("Adult");
		return movieDto;
	}
}
