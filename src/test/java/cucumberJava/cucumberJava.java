package cucumberJava;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;
import static sun.misc.Version.print;

public class cucumberJava {
    WebDriver driver = null;

    @Given("^I have opened the browser$")
    public void openBrowser() {
        System.setProperty("webdriver.gecko.driver", "C:\\install\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @When("^I navigate to the OPG login page$")
    public void goToLoginPage() {
        driver.navigate().to("http://bnwci01.westcoast.co.uk:8080/opg");
    }

    @Then("^The header label should read \"(.*?)\"$")
    public void loginButton(String headerText) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".defaultBG2Image > p:nth-child(1) > font:nth-child(1) > label:nth-child(1)")));
        WebElement header = driver.findElement(By.cssSelector(".defaultBG2Image > p:nth-child(1) > font:nth-child(1) > label:nth-child(1)"));
        String text1 = header.getText().trim();
        System.out.print("Comparing expected: "+headerText+" to found "+text1);
        assertEquals(text1, headerText);
        try {
            driver.quit();
        } catch (Exception e) {
        }
    }
}
