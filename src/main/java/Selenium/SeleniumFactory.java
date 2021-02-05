package Selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

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
			//WebDriverManager.getInstance(DriverManagerType.CHROME).useMirror().forceDownload().clearDriverCache().setup();
			WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
			mdriver = new ChromeDriver();
			mdriver.manage().timeouts().implicitlyWait(defaultTimeOut, TimeUnit.SECONDS);
			break;
		case "FireFox":
			//WebDriverManager.getInstance(DriverManagerType.FIREFOX).useMirror().forceDownload().clearDriverCache().setup();
			WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
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
