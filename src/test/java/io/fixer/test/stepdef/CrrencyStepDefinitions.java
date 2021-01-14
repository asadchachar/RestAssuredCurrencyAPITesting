package io.fixer.test.stepdef;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.fixer.test.runner.Config;
import io.fixer.test.stepdef.model.FixerRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class CrrencyStepDefinitions {
	
	private RequestSpecification requestSpecification;
	private ValidatableResponse responseValidator;
	private Response response;
	
	@Given("I have Initialized API Service call for fixer Currency Conversion API")
	public void iHaveCurrencyAPI() {
		requestSpecification = given()
				.header("api-key", Config.API_KEY)
				.baseUri(Config.BASE_URL)
				.contentType(ContentType.JSON);
	}
	
	// without API key
	@Given("I have Initialized API Service call for fixer Currency Conversion API Without api key")
	public void iHaveCurrencyAPIWitoutApiKey() {
		requestSpecification = given()
				.baseUri(Config.BASE_URL)
				.contentType(ContentType.JSON);
	}
	
	@When("I want to convert {double} {string} to {string}")
	public void i_Hit_API_with_product_id_as(Double amount, String from, String to) {
		response = requestSpecification
				.when()
					.params("from", from, "to", to, "amount", amount)
				.get(Config.RELATIVE_URL_GET);
	}
	
	@And("Verify that the response after conversion is {string}")
	public void status_code_comes_as(String code) {
		
		responseValidator = response
				.then()
				.statusCode(Config.findHttpStatus(code));
	}

	@Then("The response body contain {string} as {string}")
	public void json_body_contain_product_id_as(String key, String value) {
		
		if(value.equalsIgnoreCase("Non Null"))
			responseValidator.body(key, notNullValue());
		else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))
			responseValidator.body(key, equalTo(Boolean.valueOf(value)));
		else
			responseValidator.body(key, equalTo(value));
			
	}
	
	@When("I want to current following currencies and amount")
	public void i_Hit_API_with_product_id_as(DataTable table) throws JsonProcessingException {
		List<FixerRequest> request = table.cells().stream().skip(1)
				.map(c -> new FixerRequest(c.get(0), c.get(1), Double.valueOf(c.get(2)))).collect(Collectors.toList())
			;
		System.out.println("POST OBJ: " + request.get(0));
		ObjectMapper om = new ObjectMapper();
		String payload = om.writeValueAsString(request.get(0));
		
		response = requestSpecification
			.given()
			.body(payload)
			.post(Config.RELATIVE_URL_POST);

	}

	
}
