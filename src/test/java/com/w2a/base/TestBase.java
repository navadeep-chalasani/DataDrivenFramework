package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;



public class TestBase {
	
	  /*
	   * Here we initialize the following
	   * WebDriver
	   * Properties
	   * Logs
	   * ExtentReports
	   * Excel
	   * Mail
	   * */
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger"); 
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait ;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	
	@BeforeSuite
	public void setUp() {
		
//		BasicConfigurator.configure();
		
		if(driver == null) {
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("config file loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		if(config.getProperty("browser").equals("firefox")) {
			
			System.setProperty("wedriver.gecko.driver", System.getProperty("user.dir")+ "\\src\\test\\resources\\executables\\geckodriver.exe");
			driver = new FirefoxDriver();
			log.debug("FireFox launced!!!");
		}
		else if(config.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("Chrome Launched!!!");
		}
		else if(config.getProperty("browser").equals("ie")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			log.debug("IE launched!!!");
		}
		
		driver.get(config.getProperty("testsiteurl"));
		log.debug("navigated to "+config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
		
		
	}
	
	@AfterSuite
	public void tearDown() {
		
		if(driver != null) {
			driver.quit();
		}	
		
		log.debug("test execution completed");
		
	}
	
	public void click(String locator) {

		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();

		test.log(LogStatus.INFO, "CLicking on the "+locator);
	}
	
	static WebElement dropdown;
	
	public void select(String locator, String value ) {
		
		dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		
		test.log(LogStatus.INFO, "Selecting from the dropdown : "+locator+" value as "+value);

		
		
	}
	
	public void type(String locator, String value) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		test.log(LogStatus.INFO, "Typing "+value+" in "+locator);
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		}
		catch(NoSuchElementException e) {
			return false;
		}
		
	}
	
	public static void verifyEquals(String expected, String actual) {
		
		try {
			
			Assert.assertEquals(actual, expected);
			
		}
		catch (Throwable t) {
			TestUtil.captureScreenshot();
			//For ReportNg
			Reporter.log("<br>"+"Verifiction Failure : "+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href=\"+TestUtil.screenShotName+\"> <img src="+TestUtil.screenShotName+" height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			// For Extent rpeorts
			test.log(LogStatus.FAIL, "Verification Failed with expception"+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenShotName));

			
			
		}
	}

}
