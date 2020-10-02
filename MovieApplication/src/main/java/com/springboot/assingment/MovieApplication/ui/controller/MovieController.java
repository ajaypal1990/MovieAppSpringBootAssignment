package com.springboot.assingment.MovieApplication.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.assingment.MovieApplication.Exception.MovieServiceException;
import com.springboot.assingment.MovieApplication.service.MovieService;
import com.springboot.assingment.MovieApplication.ui.request.MovieDetailRequestModel;
import com.springboot.assingment.MovieApplication.ui.responce.ErrorMessages;
import com.springboot.assingment.MovieApplication.ui.responce.MovieRest;
import com.springboot.assingment.MovieApplication.ui.responce.OperationStatusModel;
import com.springboot.assingment.MovieApplication.ui.responce.RequestOperationName;
import com.springboot.assingment.MovieApplication.ui.responce.RequestOperationStatus;
import com.springboot.assingment.MovieApplication.ui.shared.dto.MovieDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("movies")
public class MovieController {

	@Autowired
	MovieService movieService;

	ModelMapper modelMapper = new ModelMapper();

	@PostMapping
	@ApiOperation(value = "Create a new Movie", response = MovieRest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a new Movie"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
	public MovieRest createMovie(@RequestBody MovieDetailRequestModel moviedetails) {
		MovieRest returnValue = new MovieRest();

		MovieDto movieDto = modelMapper.map(moviedetails, MovieDto.class);

		MovieDto createdMovie = movieService.createMovie(movieDto);
		returnValue = modelMapper.map(createdMovie, MovieRest.class);

		return returnValue;
	}

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Retrieve specific Movie with the supplied movie id", response = MovieRest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the Movie with the movie id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
	public MovieRest getMovie(@PathVariable String id) {
		MovieRest returnValue = new MovieRest();

		MovieDto getMovie = movieService.getMovieByMovieId(id);
		if (getMovie == null)
			throw new MovieServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		// modelMapper.map(getUser, MovieRest.class);
		BeanUtils.copyProperties(getMovie, returnValue);

		return returnValue;
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Update a Movie information", response = MovieRest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated Movie information"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
	public MovieRest updateMovie(@PathVariable String id, @RequestBody MovieDetailRequestModel moviedetails) {
		MovieRest returnValue = new MovieRest();

		if (moviedetails.getTitle() == null || moviedetails.getTitle().isEmpty())
			throw new MovieServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		modelMapper.map(moviedetails, MovieDto.class);

		MovieDto updatedMovie = movieService.updateMovie(id, moviedetails);

		BeanUtils.copyProperties(updatedMovie, returnValue);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	 @ApiOperation(value = "Deletes specific Movie with the supplied movie id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deletes the specific Movie"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
	public OperationStatusModel deleteMovie(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		movieService.deleteMovie(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping
    @ApiOperation(value = "View all Movies", response = MovieRest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all Movies"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
	public List<MovieRest> getMovies(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "5") int limit) {

		List<MovieRest> returnValue = new ArrayList<MovieRest>();

		List<MovieDto> movies = movieService.getMovies(page, limit);
		for (MovieDto user : movies) {
			MovieRest userRest = new MovieRest();
			 BeanUtils.copyProperties(user, userRest);
			returnValue.add(userRest);
		}

		return returnValue;
	}

}
