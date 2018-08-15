package Selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;

public class SeleniumFactory {
	private static WebDriver mdriver;
	private static WebDriverWait wait;
	
	protected SeleniumFactory(){
		//default constructor
	}
	
	protected SeleniumFactory(WebDriver paramDriver, long defaultTimeOut){
		mdriver = paramDriver;
		mdriver.manage().timeouts().implicitlyWait(defaultTimeOut, TimeUnit.SECONDS);
	}
	
	protected WebDriver getWebDriver(String webDriverType, long defaultTimeOut) throws Exception{		
		switch(webDriverType){
		case "Chrome":
			//System.setProperty("webdriver.chrome.driver", TestNgTestRunner.class.getClassLoader().getResource("chromedriver.exe").toExternalForm());
			ChromeDriverManager.getInstance().setup();
			mdriver = new ChromeDriver();
			mdriver.manage().timeouts().implicitlyWait(defaultTimeOut, TimeUnit.SECONDS);
			break;
		case "FireFox":
			FirefoxDriverManager.getInstance().setup();
			mdriver = new FirefoxDriver();
			mdriver.manage().timeouts().implicitlyWait(defaultTimeOut, TimeUnit.SECONDS);
			break;
			default:
				throw new Exception("Only Chrome and FireFox browsers are supported for testing as of now");
		}
		return mdriver;
	}
	
	protected WebDriver getDriver(){
		return mdriver;
	}
	
}
