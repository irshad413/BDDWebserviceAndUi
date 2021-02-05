package StepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
		createWebDriver(config.getProperty("browser"));
		driver.get(arg1);
		String windowHandle = driver.getWindowHandle();
		new SeleniumFunctions().takeScreenshot(windowHandle, driver, path, "Given");
		Hooks.scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
	}

	@When("^I enter \"([^\"]*)\" in Search$")
	public void i_enter_in_Search(String arg1) {
		driver.findElement(By.xpath("//input[@title='Search']")).sendKeys(arg1);
		driver.findElements(By.xpath("//input[@value='Google Search']")).get(0).submit();
		String windowHandle = driver.getWindowHandle();
		new SeleniumFunctions().takeScreenshot(windowHandle, driver, path, "When");
		Hooks.scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
	}
	
	@Then("^search results should be displayed$")
	public void search_results_should_be_displayed() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"result-stats\"]")));
		we.getText().contains("results");
		String windowHandle = driver.getWindowHandle();
		new SeleniumFunctions().takeScreenshot(windowHandle, driver, path, "Then");
		Hooks.scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
	}
	
	@And("^I choose images in results$")
	public void i_choose_images_in_results() throws Exception {
		driver.findElement(By.xpath("//a[text()='Images']")).click();
		Thread.sleep(3000);
		String windowHandle = driver.getWindowHandle();
		new SeleniumFunctions().takeScreenshot(windowHandle, driver, path, "Then");
		Hooks.scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
		driver.quit();
		
	}

	@Then("^Exit the browser$")
	public void exit_the_browser() {
		driver.quit();
	}
	
}
