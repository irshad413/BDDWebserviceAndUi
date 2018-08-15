package StepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Selenium.SeleniumFunctions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchFunctionality extends SeleniumFunctions{
	String path = System.getProperty("EvidencePath");
	
	@Given("^I navigate to \"([^\"]*)\" Url$")
	public void i_navigate_to_Url(String arg1) throws Exception {
		createWebDriver("Chrome");
		driver.get(arg1);
		String windowHandle = driver.getWindowHandle();
		new SeleniumFunctions().takeScreenshot(windowHandle, driver, path, "Given");
	}

	@When("^I enter \"([^\"]*)\" in Search$")
	public void i_enter_in_Search(String arg1) {
		driver.findElement(By.xpath("//*[@id=\"lst-ib\"]")).sendKeys(arg1);
		driver.findElement(By.xpath("//*[@id=\"lst-ib\"]")).submit();
		String windowHandle = driver.getWindowHandle();
		new SeleniumFunctions().takeScreenshot(windowHandle, driver, path, "When");
		
	}
	
	@Then("^search results should be displayed$")
	public void search_results_should_be_displayed() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"resultStats\"]")));
		we.getText().contains("results");
		String windowHandle = driver.getWindowHandle();
		new SeleniumFunctions().takeScreenshot(windowHandle, driver, path, "Then");
	}
	
	@And("^I choose images in results$")
	public void i_choose_images_in_results() throws Exception {
		driver.findElement(By.xpath("//*[@id=\"hdtb-msb-vis\"]/div[2]/a")).click();
		Thread.sleep(3000);
		String windowHandle = driver.getWindowHandle();
		new SeleniumFunctions().takeScreenshot(windowHandle, driver, path, "When");
		driver.quit();
		
	}

	@Then("^Exit the browser$")
	public void exit_the_browser() {
		driver.quit();
	}
	
}
