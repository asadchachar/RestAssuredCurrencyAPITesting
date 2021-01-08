package io.fixer.test.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features= {"classpath:features"},
		glue="stepdef",
		plugin=  {
				"pretty",
				"html:test-output/cucumberreport"
		},
		tags = "@FixerAPIIntegrationTests",
		publish = true

		)
public class RunnerTest {

}
