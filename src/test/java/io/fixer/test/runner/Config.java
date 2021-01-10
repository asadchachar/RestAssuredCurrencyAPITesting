package io.fixer.test.runner;

import org.apache.http.HttpStatus;

public class Config {
	public static final String BASE_URL = "http://localhost:8080/fixer";
	public static final String API_KEY = "dUYejUupPl39gip5f1wzTjdsLHHOGoOV";
	
	public static int findHttpStatus(String code) {
		if(code.equalsIgnoreCase("OK"))
			return HttpStatus.SC_OK;
		else if(code.equalsIgnoreCase("BadRequest"))
			return HttpStatus.SC_BAD_REQUEST;
		else if(code.equalsIgnoreCase("Unauthorized"))
			return HttpStatus.SC_UNAUTHORIZED;
		return HttpStatus.SC_BAD_GATEWAY;
	}
}
