package Selenium;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import StepDefinitions.Hooks;
import Utils.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class SeleniumFunctions{
	public static Config config = Config.getInstance();
	public static WebDriver driver = null;
	public static WebDriverWait waitVar = null;
	
	public void createWebDriver(String driverType) throws Exception{
		driver = new SeleniumFactory().getWebDriver(driverType, 30);
		driver.manage().window().maximize();
	}
	
	public WebDriver createDriver(String url){
		/*System.setProperty("webdriver.chrome.driver",
				TestNgTestRunner.class.getClassLoader().getResource("chromedriver.exe").toExternalForm());*/
		WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
		ChromeOptions o = new ChromeOptions();
		o.addArguments("disable-extensions");
		o.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(o);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		waitVar = new WebDriverWait(driver, 30);
		return driver;
	}
	
	public WebDriver createInCognito(String url){
		/*System.setProperty("webdriver.chrome.driver",
				TestNgTestRunner.class.getClassLoader().getResource("chromedriver.exe").toExternalForm());*/
		WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
		ChromeOptions o = new ChromeOptions();
		o.addArguments("incognito");
		o.addArguments("disable-extensions");
		o.addArguments("--start-maximized");
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(o.CAPABILITY, o);
		WebDriver driver = new ChromeDriver(o);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		waitVar = new WebDriverWait(driver, 30);
		return driver;
	}
	
	public void teardown(){
		driver.quit();
	}
	
	public void isHomePageDisplayed(){
		//waitVar.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Log In/Sign Up")));	
	}
	//Hooks.scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
	public void takeScreenshot(String windowHandle, WebDriver driver, String outputFilePath, String step){
		try{
			driver.switchTo().window(windowHandle);
			File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(image, new File(outputFilePath+"\\"+step+".jpg"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
