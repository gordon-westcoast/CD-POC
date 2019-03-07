package cucumberJava;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class cucumberJava {
    WebDriver driver = null;

    @Given("^I have open the browser$")
    public void openBrowser() {
        System.setProperty("webdriver.gecko.driver", "C:\\install\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @When("^I open Facebook website$")
    public void goToFacebook() {
        driver.navigate().to("https://www.facebook.com/");
    }

    @Then("^Login button should exist$")
    public void loginButton() {
        if(driver.findElement(By.id("u_0_v")).isEnabled()) {
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