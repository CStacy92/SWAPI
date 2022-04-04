package com.swapiTests;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SwapiTests {

	@Test
	public void validateStatusCode() {

		RestAssured.baseURI = "https://swapi.dev/api/";
		RequestSpecification httpRequest = RestAssured.given();
		Response resp = httpRequest.get("/people");

		// verify status code
		Assert.assertTrue(resp.statusCode() == 200);

	}

	@Test
	public void heightsAndNames() {

		RestAssured.baseURI = "https://swapi.dev/api/";
		RequestSpecification httpRequest = RestAssured.given();

		// count height greater than 200cm in response
		// verify names of characters over 200cm in height
		ArrayList<String> amounts = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> temp = new ArrayList<String>();
		ArrayList<String> over200 = new ArrayList<String>(Arrays.asList("Darth Vader", "Chewbacca", "Jar Jar Binks",
				"Roos Tarpals", "Saesee Tiin", "Dexter Jettster", "Lama Su", "Shaak Ti", "Grievous", "Sly Moore"));
		for (int i = 1; i < 10; i++) {
			String page = "/people/?page=" + i;
			temp = httpRequest.get(page).then().extract().path("results.height");
			for (String j : temp) {
				amounts.add(j);
			}
			temp.clear();
			temp = httpRequest.get(page).then().extract().path("results.name");
			for (String na : temp) {
				names.add(na);
			}
			temp.clear();
		}

		int i = 0;
		int count = 0;
		for (String h : amounts) {
			if (h.equals("unknown")) {
				continue;
			} else {
				int x = Integer.valueOf(h);
				if (x > 200) {
					count++;
					temp.add(names.get(i));
				}
			}
			i++;
		}
		Assert.assertEquals(over200, temp);
		Assert.assertEquals(10, count);

		// verify number of people checked
		Assert.assertEquals(82, amounts.size());

	}

}
