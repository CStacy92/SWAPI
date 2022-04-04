package com.swapiTests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SwapiTests {

	@Test
	public void validation() {
		RestAssured.baseURI = "https://swapi.dev/api/";
		RequestSpecification httpRequest = RestAssured.given();
		Response resp = httpRequest.get("/people");

		//JsonPath people = resp.jsonPath();

		//verify status code
		Assert.assertTrue(resp.statusCode() == 200);
		
		//count height greater than 200 in response
		//fix (NOT CHECKING ALL 82 people)
		List heights = resp.body().jsonPath().getList("results.height");
		int count = 0;
		
		for (Object h : heights) {
			int x = Integer.valueOf(h.toString());
			if (x > 200) {
				count++;
			}
		}
		Assert.assertEquals(1, count);
		
	}

}
