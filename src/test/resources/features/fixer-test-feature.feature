@FixerAPIIntegrationTests
Feature: Currency Conversion
	
	@testConvertCurrencyGETAPI
	Scenario: Verify currency Conversion for GET Request 1.0
	  Given I have Currency Conversion REST API
    When I Hit API with from "USD" to "PKR" amount 10.5
		Then status code comes as 200
		And json body contain success as "true"
		And response contains from currency as "USD"
		
	@testConvertCurrencyGETAPI_Extended
	Scenario: Verify currency Conversion for GET Request 2.0
		Given I have Currency Conversion REST API
		When I have following data for conversion
			| from	| to 	| amount	|
			| SEK		| NOK | 10.9		|
			| DKK		| EUR | 290.1		|
			| PKR		| JPY | 33			|
		Then Responses statuses must be 200
		And Responses converted rate must not be null
	
		@testConvertCurrencyPOSTAPI
		Scenario: Verify currency Conversion for POST Request
			Given I have Currency Conversion POST API
			When I Hit API with POST Data
				| from 	| to 	| amount 	|
				| PKR		| JPY	| 33			|
			Then response status code comes of 200
			And response body contain success as "true"
			And response body contains to currency as "JPY"
		
	
