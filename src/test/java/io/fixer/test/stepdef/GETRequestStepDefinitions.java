package io.fixer.test.stepdef;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.fixer.test.runner.Config;
import io.fixer.test.stepdef.model.FixerRequest;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class GETRequestStepDefinitions {
	private RequestSpecification requestSpecification;
	private List<Response> responseList = new ArrayList<>();
	private ValidatableResponse responseValidator;
	private Response response;
	
	@Given("I have Currency Conversion REST API")
	public void iHaveCurrencyAPI() {
		requestSpecification = given()
				.header("api-key", Config.API_KEY)
				.baseUri(Config.BASE_URL);
	}

	@When("I Hit API with from {string} to {string} amount {double}")
	public void i_Hit_API_with_product_id_as(String from, String to, Double amount) {
		response = requestSpecification
				.when()
					.params("from", from, "to", to, "amount", amount)
				.get("/convert");
	}

	@And("status code comes as {int}")
	public void status_code_comes_as(Integer int1) {
		responseValidator = response
				.then()
				.statusCode(int1);
	}

	@Then("json body contain success as {string}")
	public void json_body_contain_product_id_as(String flag) {
		responseValidator.body("success", equalTo(Boolean.valueOf(flag)));
	}
	
	@And("response contains from currency as {string}")
	public void responseContainsFromCurrency(String fromCurrency) {
		responseValidator.body("from", equalTo(fromCurrency));
	}

	/*
	 * Extended
	 */

	@When("I have following data for conversion")
	public void i_Hit_API_with_Data_Table(DataTable table) {
		
		table.cells().stream().skip(1).forEach( param -> {
			responseList.add(
					requestSpecification
					.when()
						.params("from", param.get(0), "to", param.get(1), "amount", param.get(2))
					.get("/convert")
			);
			
		});	
		
	}
	
	@Then ("Responses statuses must be {int}")
	public void responseStatusesMustBe(Integer status) {
		System.out.println("validating response");

		responseList.get(0)
			.then()
				.statusCode(status)
				.body("success", equalTo(true), 
						"rate", notNullValue());
		
//		responseList.stream().forEach( r -> {
//			System.out.println(r.statusCode());
//			r.then().statusCode(status);
//		});
		System.out.println("response validation done");

	}
	
	@And("Responses converted rate must not be null")
	public void responsesConvertedRateNotNull() {
		responseList.get(0)
			.then()
			.body("convertResult", notNullValue(), 
					"rate", notNullValue());
	}

/*
	private Response callAPI(FixerRequest param) {
		System.out.println("Calling api "+ param);
		RestAssured.baseURI = baseUri;
		RequestSpecification request = RestAssured.given()
				.header("api-key", "dUYejUupPl39gip5f1wzTjdsLHHOGoOV");

		return request.queryParam("from",param.getFrom())
		        .queryParam("to", param.getTo())
		        .queryParam("amount", param.getAmount())
	        .get("/convert");
	}
*/
	
}
