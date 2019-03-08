package cucumberJava;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class cucumberJava {
    WebDriver driver = null;

    @Given("^I have opened the browser$")
    public void openBrowser() {
        System.setProperty("webdriver.gecko.driver", "C:\\install\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @When("^I navigate to the home page$")
    public void goToHomePage() {
        driver.navigate().to("http://bnwci01.westcoast.co.uk/");
    }

    @Then("^The header label should exist$")
    public void loginButton() {
        if(driver.findElement(By.cssSelector("html body nav.navbar.navbar-inverse.navbar-fixed-top div.container div.navbar-header a.navbar-brand")).isEnabled()) {
            System.out.println("Test 1 Pass");
        } else {
            System.out.println("Test 1 Fail");
        }
        try {
            driver.quit();
        }
        catch(Exception e){}
    }
}