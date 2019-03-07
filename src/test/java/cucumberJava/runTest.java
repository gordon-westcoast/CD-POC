package cucumberJava;

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", "html:target/cucumber"})

public class runTest { }