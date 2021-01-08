package io.fixer.test.stepdef;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

public class POSTRequestStepDefinitions {
	private RequestSpecification requestSpecification;
	private ValidatableResponse responseValidator;
	private Response response;
	
	@Given("I have Currency Conversion POST API")
	public void iHaveCurrencyAPI() {
		requestSpecification = given()
				.header("api-key", Config.API_KEY)
				.baseUri(Config.BASE_URL)
				.contentType(ContentType.JSON);
	}

	@When("I Hit API with POST Data")
	public void i_Hit_API_with_product_id_as(DataTable table) throws JsonProcessingException {
		Optional<FixerRequest> request = table.cells().stream().skip(1)
				.map(c -> new FixerRequest(c.get(0), c.get(1), Double.valueOf(c.get(2))))
				.findFirst();
		System.out.println("POST OBJ: " + request.get());
		ObjectMapper om = new ObjectMapper();
		String payload = om.writeValueAsString(request.get());
		
		response = requestSpecification
			.given()
			.body(payload)
			.post("/convert/currency");

	}

	@Then("response status code comes of {int}")
	public void status_code_comes_as(Integer responseCode) {
		responseValidator = response
				.then()
				.statusCode(responseCode);
	}

	@And("response body contain success as {string}")
	public void responseBodyContainSuccessAs(String success) {
		responseValidator.body("success", equalTo(Boolean.valueOf(success)));
	}
	
	@And("response body contains to currency as {string}")
	public void responseBodyContainsToCurrencyAs(String toCurrency) {
		responseValidator.body("to", equalTo(toCurrency));
	}

	
}
