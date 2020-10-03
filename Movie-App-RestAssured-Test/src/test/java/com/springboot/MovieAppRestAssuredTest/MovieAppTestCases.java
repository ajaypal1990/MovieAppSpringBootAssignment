package com.springboot.MovieAppRestAssuredTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.response.Response;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MovieAppTestCases {

	private final String CONTEXT_PATH = "/movie-app";
	private static String movieId;

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8095;
	}
/**
 * testCreateMovie()
 */
	@Test
	void a() {

		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("title", "Thor"); // should be unique record
		hashMap.put("category", "Action");
		hashMap.put("starRating", "3");
		
		Response responce = RestAssured.given().
				contentType("application/json")
				.accept("application/json")
				.body(hashMap)
				.when()
				.post(CONTEXT_PATH + "/movies")
				.then()
				.statusCode(200)
				.contentType("application/json")
				.extract()
				.response();
		
		 movieId=responce.jsonPath().getString("movieId");
		assertNotNull(movieId);
		
		String bodyString=responce.getBody().asString();
		
		try {
			JSONObject responceBodyJson=new JSONObject(bodyString);
			assertNotNull(responceBodyJson);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * testGetMovie()
	 */
	@Test
	void b() {

		
		Response responce = RestAssured.given().
				pathParam("movieId", movieId).
				contentType("application/json")
				.accept("application/json")
				.when()
				.get(CONTEXT_PATH + "/movies/{movieId}")
				.then()
				.statusCode(200)
				.contentType("application/json")
				.extract()
				.response();
		
		String publicMovieId=responce.jsonPath().getString("movieId");
		String movieTitle=responce.jsonPath().getString("title");
		String category=responce.jsonPath().getString("category");
		String starRating=responce.jsonPath().getString("starRating");
		assertNotNull(publicMovieId);
		assertNotNull(movieTitle);
		assertNotNull(category);
		assertNotNull(starRating);
	}
	
	/**
	 * testUpdateMovie
	 */
	@Test
	final void c()
	{
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("title", "UpdatedTest4");
		hashMap.put("category", "UpdatedTestFinal");
		hashMap.put("starRating", "3");
		
		 Response response = RestAssured.given()
		 .contentType("application/json")
		 .accept("application/json")
		 .pathParam("movieId", movieId)
		 .body(hashMap)
		 .when()
		 .put(CONTEXT_PATH + "/movies/{movieId}")
		 .then()
		 .statusCode(200)
		 .contentType("application/json")
		 .extract()
		 .response();
		 
         String title = response.jsonPath().getString("title");
         String category = response.jsonPath().getString("category");
         String starRating = response.jsonPath().getString("starRating");
         
         
         assertEquals("UpdatedTest4", title);
         assertEquals("UpdatedTestFinal", category);
         assertEquals("3", starRating);
         
	}
	
	/*
	 * Test the Delete Movie
	 * */
	@Test
	final void d()
	{
		Response response = RestAssured.given()
		.accept("application/json")
		.pathParam("movieId", movieId)
		.when()
		.delete(CONTEXT_PATH + "/movies/{movieId}")
		.then()
		.statusCode(200)
		.contentType("application/json")
		.extract()
		.response();
		
		String operationResult = response.jsonPath().getString("operationResult");
		assertEquals("SUCCESS", operationResult);
		
	}

}
