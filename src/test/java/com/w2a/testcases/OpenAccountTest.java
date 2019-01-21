package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class OpenAccountTest extends TestBase{
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {
		
		if(TestUtil.isTestRunnable("openAccountTest", excel)) {
					
					throw new SkipException("Skipping the test case "+"openAccountTest".toUpperCase() +" as the runmode is No");
					
				}
		
		log.debug("Inside Open Account class");
		click("openaccount");
		select("customer", data.get("customer"));
		select("currency", data.get("currency"));
		click("process");
		
		Thread.sleep(3000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		
		Thread.sleep(2000);
		log.debug("open Account test sucessfully completed");

	}

}
