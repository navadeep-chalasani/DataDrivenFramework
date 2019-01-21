package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;



public class AddCustomerTest extends TestBase {
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {
		
		if(data.get("runmode").equalsIgnoreCase("N")) {
			throw new SkipException("Skipping the test case because the runmode of the test data is NO");
		}
		
		log.debug("Inside Add Customer Test case");
		click("addCustBtn");
		type("firstName", data.get("firstName"));
		type("lastName", data.get("lastName"));
		type("postCode", data.get("postCode"));
		click("addbtn");
		
		Thread.sleep(2000);
		
	/*	driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();
		driver.findElement(By.cssSelector(OR.getProperty("firstName"))).sendKeys(firstName);
		driver.findElement(By.cssSelector(OR.getProperty("lastName"))).sendKeys(lastName);
		driver.findElement(By.cssSelector(OR.getProperty("postCode"))).sendKeys(postCode);
		driver.findElement(By.cssSelector(OR.getProperty("addbtn"))).click();
		*/
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		Thread.sleep(3000);

		alert.accept();
				
		log.debug("Add Customer test sucessfully");
	}
	
}
