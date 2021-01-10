@FixerAPIIntegrationTests
Feature: Test Currency Conversion API
	In order to verify currency API works fine
	We want to input *toCurrency*, *fromCurrency* and *amount* to be converted 
	
@VerifyThatGivenTwoCurrenciesAreConvertedAsPerGivenCurrencyAmount
Scenario: To Verify that out of two currencies and amount converts successfully 
	Given I have Initialized API Service call for fixer Currency Conversion API
	When I want to convert 100.5 "SEK" to "PKR"
	Then Verify that the response after conversion is "OK"
	And The response body contain "success" as "true"
	And The response body contain "rate" as "Non Null"

@VerifyThatGivenTwoCurrenciesAreConvertedAsPerGivenAmountGETRequest
Scenario Outline: To Verify that given two currencies and amount converts successfully with data from fixer
	Given I have Initialized API Service call for fixer Currency Conversion API
	When I want to convert <amount> <fromCurrency> to <toCurrency>
	Then Verify that the response after conversion is "OK"
	And The response body contain "from" as <fromCurrency>
	And The response body contain "success" as "true"
	And The response body contain "rate" as "Non Null"
	And The response body contain "convertResult" as "Non Null"
	Examples:
		| amount 		| fromCurrency | toCurrency		|
		| 1587   		| "USD"        | "PKR"     		|
		| 222.56   	| "NOK"        | "EUR"     		|
		| 100.09   	| "DKK"        | "SEK"     		|

@VerifyThatGivenTwoCurrenciesAreConvertedAsPerGivenAmountPOSTRequest
Scenario Outline: To Verify Given two currencies and amount converts successfully 
	Given I have Initialized API Service call for fixer Currency Conversion API
	When I want to convert <amount> <fromCurrency> to <toCurrency>
	Then Verify that the response after conversion is <responseStatus>
	Examples: 
		| fromCurrency	| toCurrency			| amount	| responseStatus 	 |
		| 		"NOK"				| 		"PKR"			| 10.5		| "OK"             |
		| 		"DKK"				|		  "SEK"			| 44.5		| "OK"             |
		| 		"CCCZ"			| 		"SEK"			| 20.0		| "BadRequest"     |
		| 		"NOK"				| 		"BBCC"		| 22.7		| "BadRequest"     |
		| 		"NOK"				| 		"INR"			| 0.0			| "BadRequest"     |

@VerifyThatGivenTwoCurrenciesAreConvertedOnGivenAmount
Scenario: To Verify that Given two currencies converts successfully 
	Given I have Initialized API Service call for fixer Currency Conversion API
	And I want to current following currencies and amount
		|	from							|	to						|	amount		|
		|	<fromCurrency>		|	<toCurrency>	|	<amount>	|
	Then Verify that the response after conversion is <responseStatus>
	Examples:
		| fromCurrency	| toCurrency	| amount	| responseStatus |
		| 		NOK				| 		PKR			| 10.5		| "OK"           |

#without API Key
@VerifyThatCallingCurrencyConversionAPIWithoutAPIKeyThrowsUNAUTHORIZEDErrorCode
Scenario Outline: To Verify that any request with invalid API key in header will throw 401 UnAuthorized Error respose from server
	Given I have Initialized API Service call for fixer Currency Conversion API Without api key
	When I want to convert <amount> <fromCurrency> to <toCurrency>
	Then Verify that the response after conversion is <responseStatus>

	Examples:
		| fromCurrency 	| toCurrency 	| amount 	 	| responseStatus 	|
		|  "NOK"				|  "INR"			| 2880.0		| "Unauthorized" |
		
