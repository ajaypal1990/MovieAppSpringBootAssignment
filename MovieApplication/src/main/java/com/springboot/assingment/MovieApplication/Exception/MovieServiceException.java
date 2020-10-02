package com.springboot.assingment.MovieApplication.Exception;

public class MovieServiceException extends RuntimeException{
	private static final long serialVersionUID = -6934194105536052897L;

	
	public MovieServiceException(String message) {
		super(message);
	}
}
