package com.w2a.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException {
//		System.setProperty("org.uncommons.reportng.escape-output", "false");
		
		verifyEquals("abc","xyz");
		Thread.sleep(3000);
		log.debug("Inside Log in test");
		/*driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();*/
		click("bmlBtn");
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))), "Login not sucessfull");
		Thread.sleep(3000);
//		Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\Khulesh Gupta\\Pictures\\joneses\\error.png\">Screenshot</a>");

		log.debug("Log in successfully executed");
//		Assert.fail();		
	}

}

