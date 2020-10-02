package com.springboot.assingment.MovieApplication;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.assingment.MovieApplication.Exception.MovieServiceException;
import com.springboot.assingment.MovieApplication.io.entity.MovieEntity;
import com.springboot.assingment.MovieApplication.io.entity.MovieRespository;
import com.springboot.assingment.MovieApplication.service.impl.MovieServiceImpl;
import com.springboot.assingment.MovieApplication.ui.request.MovieDetailRequestModel;
import com.springboot.assingment.MovieApplication.ui.shared.Utils;
import com.springboot.assingment.MovieApplication.ui.shared.dto.MovieDto;

@SpringBootTest
class MovieApplicationTests {

	@InjectMocks
	private MovieServiceImpl movieServiceImpl;

	@Mock
	MovieRespository repository;

	@Mock
	Utils util;

	MovieEntity movieEntity;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		movieEntity = new MovieEntity();
		movieEntity.setId(1L);
		movieEntity.setMovieId("y4ecu");
		movieEntity.setTitle("Avenger");
		movieEntity.setStarRating(3L);
		movieEntity.setCategory("Adult");
	}

	@Test
	void CreateMovieWithExistingRecord() {
		MovieDto movie = createMovie();
		Mockito.when(repository.findByTitle(Mockito.anyString()))
				.thenThrow(new MovieServiceException("Record already exists"));
		Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("y4ecu");
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
		Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(movieEntity);

		Exception exception = assertThrows(MovieServiceException.class, () -> {
			MovieDto movieDTO = movieServiceImpl.createMovie(movie);
		});

	}

	@Test
	void CreateMovieTest() {
		MovieDto movie = createUniqueMovie();
		Mockito.when(repository.findByTitle(Mockito.anyString())).thenReturn(null);
		// Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("dsfh");
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
		// Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(movieEntity);
		MovieDto movieDTO = movieServiceImpl.createMovie(movie);
		Assertions.assertNotNull(movieDTO);
		Assertions.assertEquals("Avenger", movieDTO.getTitle());
	}

	@Test
	void CreateMovieRatingTest() {
		MovieDto movie = createMovieWithInvalidRating();
		Mockito.when(repository.findByTitle(Mockito.anyString()))
				.thenThrow(new MovieServiceException("Invalid Rating Value,Rate Between 1-5 "));
		// Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("dsfh");
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
		// Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(movieEntity);
		Exception exception = assertThrows(MovieServiceException.class, () -> {
			MovieDto movieDTO = movieServiceImpl.createMovie(movie);
		});
	}

	@Test
	void getMovieTest() {
		Mockito.when(repository.findByTitle(Mockito.anyString())).thenReturn(movieEntity);
		Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("y4ecu");
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
		Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(movieEntity);
		MovieDto movieDto = movieServiceImpl.getMovieByMovieId("y4ecu");
		Assertions.assertNotNull(movieDto);
		Assertions.assertEquals("Avenger", movieDto.getTitle());
	}

	@Test
	void getMovieThrowNullPointerException() {
		Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(null);
		Exception exception = assertThrows(NullPointerException.class, () -> {
			MovieDto movieDto = movieServiceImpl.getMovieByMovieId("y4ecu");
		});
	}

	@Test
	void UpdateMovieTest() {
		MovieDto movie = updateMovie();
		Mockito.when(repository.findByTitle(Mockito.anyString())).thenReturn(null);
		Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("dsfh");
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
		Mockito.when(repository.findByMovieId(Mockito.anyString())).thenReturn(movieEntity);
		MovieDto movieDTO = movieServiceImpl.updateMovie("y4ecu", getMovieDetailRequestModel());
		Assertions.assertNotNull(movieDTO);
		Assertions.assertEquals("UpdatedThor", movieDTO.getTitle());
	}

	@Test
	void DeleteMovieTest() {
		MovieDto movie = updateMovie();
		// Mockito.when(repository.findByTitle(Mockito.anyString())).thenThrow(new
		// MovieServiceException("Record with provided id is not found"));
		Mockito.when(util.generateMovieId(Mockito.anyInt())).thenReturn("dsfh");
		Mockito.when(repository.save(Mockito.anyObject())).thenReturn(movieEntity);
		Mockito.when(repository.findByMovieId(Mockito.anyString()))
				.thenThrow(new MovieServiceException("Record with provided id is not found"));

		Exception exception = assertThrows(MovieServiceException.class, () -> {
			movieServiceImpl.deleteMovie("y4ecu");
		});
	}

	private MovieDto createMovie() {
		MovieDto movie = new MovieDto();
		movie.setCategory("Adult");
		movie.setStarRating(3L);
		movie.setTitle("Avenger");
		movie.setId(1L);
		movie.setMovieId("y4ecu");
		return movie;
	}

	private MovieDto createUniqueMovie() {
		MovieDto movie = new MovieDto();
		movie.setCategory("Adult");
		movie.setStarRating(3L);
		movie.setTitle("Thor");
		movie.setId(1L);
		movie.setMovieId("dsfh");
		return movie;
	}

	private MovieDto updateMovie() {
		MovieDto movie = new MovieDto();
		movie.setCategory("Adult");
		movie.setStarRating(3L);
		movie.setTitle("Updated");
		movie.setId(1L);
		movie.setMovieId("dsfh");
		return movie;
	}

	private MovieDto createMovieWithInvalidRating() {
		MovieDto movie = new MovieDto();
		movie.setCategory("Adult");
		movie.setStarRating(3L);
		movie.setTitle("Thor");
		movie.setId(8L);
		movie.setMovieId("dsfh");
		return movie;
	}

	private MovieEntity getUniqueMovieEntity() {
		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setId(1L);
		movieEntity.setMovieId("y4ecu");
		movieEntity.setStarRating(3L);
		movieEntity.setCategory("Adult");

		return movieEntity;
	}

	private MovieDetailRequestModel getMovieDetailRequestModel() {
		MovieDetailRequestModel movie = new MovieDetailRequestModel();
		movie.setCategory("Adult");
		movie.setStarRating(3L);
		movie.setTitle("UpdatedThor");

		return movie;
	}

}
