package com.xcira.server.webdriver;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class DealerQuotePage extends WebDriverTestBase {
	
	@Before
	public void setUp() throws Exception {
		
		driver = new FirefoxDriver();
		baseUrl = "https://ng.xcira.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		actions = new Actions(driver);
	}

	@Test
	public void testDealerQuotePage() throws Exception {
		
		driver.get(baseUrl);
		
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("ro@ro.com");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("ro");
		driver.findElement(By.cssSelector("button.signInButton")).click();

		actions.click(driver.findElement(By.xpath("//div[contains(text(),'Dealers')]"))).perform();
	    actions.click(driver.findElement(By.cssSelector("span.dgrid-last.dgrid-page-link"))).perform();
	    actions.click(driver.findElement(By.xpath("//td[contains(text(), 'andrew note')]/preceding-sibling::td"))).perform();
	    
		actions.click(driver.findElement(By.cssSelector("img.logoutIcon"))).perform();
		actions.click(driver.findElement(By.cssSelector("button.alertButton"))).perform();
	}

	@After
	public void tearDown() throws Exception {
		
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
