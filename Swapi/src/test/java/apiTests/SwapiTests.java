package apiTests;

import org.junit.Assert;
import org.junit.Test;

import files.testingData;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.util.ArrayList;
import java.util.Arrays;

public class SwapiTests {
	int count = 0;
	ArrayList<String> temp = new ArrayList<String>();
	ArrayList<String> people = new ArrayList<String>();
	ArrayList<String> heights = new ArrayList<String>();
	

	@Test
	public void statusCodeValidation() {
		RestAssured.baseURI = "https://swapi.dev/api/";
		RequestSpecification httpRequest = RestAssured.given();

		httpRequest.when().get("/people/").then().assertThat().statusCode(200);
	}

	@Test
	public void height_over_200() {
		RestAssured.baseURI = "https://swapi.dev/api/";
		RequestSpecification httpRequest = RestAssured.given();

		for (int i = 1; i < 10; i++) {
			httpRequest = httpRequest.queryParam("page", i);
			temp = httpRequest.when().get("people/").then().extract()
					.path("results.name");
			for(String n : temp) {
				people.add(n);
			}
			temp.clear();
			temp = httpRequest.when().get("people/").then().extract()
					.path("results.height");
			for(String n : temp) {
				heights.add(n);
			}
			
		}
		
		temp.clear();
		int i = 0;
		for (String h : heights) {
			if (h.equals("unknown")) {
				continue;
			} else {
				if (Integer.valueOf(h) > 200) {
					count++;
					temp.add(people.get(i));
				}
			}
			i++;
		}
		
		Assert.assertEquals(testingData.height_over_200(), temp);
		Assert.assertEquals(10, count);

		// verify number of people checked
		Assert.assertEquals(82, heights.size());
	}

}
