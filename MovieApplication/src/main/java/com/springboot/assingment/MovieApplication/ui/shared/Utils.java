package com.springboot.assingment.MovieApplication.ui.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Utils {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Random random=new SecureRandom();
	private final String Alphabets="abcdefghijklmanopqrstuvwxyz123456789";
	
	public String generateMovieId(int length) {
		
		return generateRandomString(length);
	}
	

	private String generateRandomString(int length) {
		StringBuilder returnValue=new StringBuilder(length);
		logger.info("Inside the generateRandomString method of Utils class");
		for(int i=0;i<length;i++) {
			returnValue.append(Alphabets.charAt(random.nextInt(Alphabets.length())));
		}
		return new String(returnValue);
	}
	

}
