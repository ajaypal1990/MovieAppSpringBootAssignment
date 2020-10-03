package com.springboot.assingment.MovieApplication.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.assingment.MovieApplication.Exception.MovieServiceException;
import com.springboot.assingment.MovieApplication.io.entity.MovieEntity;
import com.springboot.assingment.MovieApplication.io.entity.MovieRespository;
import com.springboot.assingment.MovieApplication.service.MovieService;
import com.springboot.assingment.MovieApplication.ui.request.MovieDetailRequestModel;
import com.springboot.assingment.MovieApplication.ui.responce.ErrorMessages;
import com.springboot.assingment.MovieApplication.ui.shared.Utils;
import com.springboot.assingment.MovieApplication.ui.shared.dto.MovieDto;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MovieRespository repository;
	
	@Autowired
	private Utils util;
	
	@Override
	public MovieDto createMovie(MovieDto movieDto) {

		if(movieDto.getStarRating()>5 || movieDto.getStarRating()<1) {
			logger.info("Inside the create Movie Method of Service Layer, seems rating is not valid");
			throw new MovieServiceException("Invalid Rating Value,Rate Between 1-5 ");
		}
		
		if (repository.findByTitle(movieDto.getTitle()) != null) {
			logger.info("Record already exists");
			throw new MovieServiceException("Record already exists");
		}
	
		//BeanUtils.copyProperties(user, userEntity);
		ModelMapper modelMapper = new ModelMapper();
		MovieEntity movieEntity = modelMapper.map(movieDto, MovieEntity.class);

		String publicMovieId = util.generateMovieId(5);
		movieEntity.setMovieId(publicMovieId);

		MovieEntity storedMovieDetails = repository.save(movieEntity);
 
		//BeanUtils.copyProperties(storedUserDetails, returnValue);
		MovieDto returnValue  = modelMapper.map(storedMovieDetails, MovieDto.class);
		return returnValue;
	}

	@Override
	public MovieDto getMovieByMovieId(String id) {
		MovieDto returnValue = new MovieDto();
		MovieEntity movieEntity = repository.findByMovieId(id);

		if (movieEntity == null)
			throw new NullPointerException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		BeanUtils.copyProperties(movieEntity, returnValue);

		return returnValue;
	}

	@Override
	public MovieDto updateMovie(String movieId, MovieDetailRequestModel moviedetails) {
		if(moviedetails.getStarRating()>5 || moviedetails.getStarRating()<1) {
			throw new MovieServiceException("Invalid Rating Value,Rate Between 1-5 ");
		}
		MovieDto returnValue = new MovieDto();
		MovieEntity movieEntity = repository.findByMovieId(movieId);

		if (movieEntity == null)
			throw new MovieServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		movieEntity.setTitle(moviedetails.getTitle());
		movieEntity.setStarRating(moviedetails.getStarRating());
		movieEntity.setCategory(moviedetails.getCategory());

		MovieEntity updatedMovieEntity = repository.save(movieEntity);

		BeanUtils.copyProperties(updatedMovieEntity, returnValue);
		return returnValue;
	}

	@Override
	public void deleteMovie(String movieId) {
		MovieEntity movieEntity = repository.findByMovieId(movieId);

		if (movieEntity == null)
			throw new MovieServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		repository.deleteByMovieId(movieId);
		
	}

	@Override
	public List<MovieDto> getMovies(int page, int limit) {
		List<MovieDto> returnValue=new ArrayList<MovieDto>();
		Pageable pageable=PageRequest.of(page, limit);
		
		Page<MovieEntity> users=repository.findAll(pageable);
		page=page-1;
		for(MovieEntity user:users) {
			MovieDto userDto=new MovieDto();
			BeanUtils.copyProperties(user, userDto);
			returnValue.add(userDto);
		}
		return returnValue;
	}


}
