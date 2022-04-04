package com.swapiTests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class SwapiTests {

	@Test
	public void validation() {
		RestAssured.baseURI = "https://swapi.dev/api/";
		RequestSpecification httpRequest = RestAssured.given();
		Response resp = httpRequest.get("/people");

		// verify status code
		Assert.assertTrue(resp.statusCode() == 200);

		// count height greater than 200 in response
		//KEEP TRACK OF NAMES WITH GREATER THAN 200 HEIGHT
		ArrayList<String> amounts = new ArrayList<String>();
		for (int i = 1; i < 10; i++) {
			String page = "/people/?page=" + i;
			ArrayList<String> h = httpRequest.get(page).then().extract().path("results.height");
			for (String j : h) {
				amounts.add(j);
			}
		}

		int count = 0;
		for (String h : amounts) {
			if (h.equals("unknown")) {
				continue;
			} else {
				int x = Integer.valueOf(h);
				if (x > 200) {
					count++;
				}
			}
		}
		Assert.assertEquals(10, count);
		
		//verify number of people checked
		Assert.assertEquals(82, amounts.size());

		// verify names taller than 200

	}

}
