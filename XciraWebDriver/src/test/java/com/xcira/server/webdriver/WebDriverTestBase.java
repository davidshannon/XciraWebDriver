package com.xcira.server.webdriver;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverTestBase extends TestBase {
	
	static final protected String BROWSER_PATH = "BROWSER_PATH";
	static final protected String BROWSER_NAME = "BROWSER_NAME";
	static final protected String GECKO_PATH = "GECKO_PATH";
	static final protected String IMPLICITE_WAIT = "IMPLICITE_WAIT";
	static final protected String SCREENSHOT_DIR = "SCREENSHOT_DIR";
	static final protected String PLATFORM= "PLATFORM";
	static final protected String VERSION="VERSION";
	static final protected String URL = "URL";
	static final protected String SAUCE_LABS_URL = "SAUCE_LABS_URL";
	
	protected Actions actions;
	protected WebDriver driver;
	protected String baseUrl;
	protected boolean acceptNextAlert = true;
	protected StringBuffer verificationErrors = new StringBuffer();
	protected List<WebElement> pageOptionButtons;

	protected WebElement verifyAttributeValues(By by, Map<String,String> attributeValues) {
		
		WebElement element = driver.findElement(by);
		
		for(String key : attributeValues.keySet()) {
		
			assertEquals(attributeValues.get(key), element.findElement(By.name("creditLimit")).getAttribute(key));
		}
		
		return element.findElement(By.name("creditLimit"));
	}
	
	protected List<WebElement> getPageOptionButtons() {
		
		return driver.findElement(By.cssSelector("div.pageOptions")).findElements(By.tagName("button"));
	}
	
	protected void clickPageOptionButton(String buttonText) {
		
		for(WebElement button : pageOptionButtons) {
			
			if(button.getText().equals(buttonText)) {
			
				actions.click(button).perform();
			}
		}
	}

	protected boolean isAlertPresent() {

		try {
		  
			driver.switchTo().alert();
		
			return true;
		
		} catch (NoAlertPresentException e) {
			  
			return false;
		}
	}

	protected String closeAlertAndGetItsText() {
	
		try {
			  
			Alert alert = driver.switchTo().alert();
		      
			String alertText = alert.getText();
		      
		    if (acceptNextAlert) {
		        
		    	alert.accept();
		      
		    } else {
		      
		    	alert.dismiss();
		    }
		      
		    return alertText;
		      
		  } finally {
			  
			acceptNextAlert = true;
		}
	}
	  
	protected boolean isElementPresent(By by) {
		  
		try {
			  
			driver.findElement(by);
		      
			return true;
		      
		} catch (NoSuchElementException e) {
			  
		   return false;
		}
	}
	
	protected boolean isElementPresent(String path) throws Exception {
		
		try {
			  
			find(path);
		      
			return true;
		      
		} catch (NoSuchElementException e) {
			  
		   return false;
		}
	}
	
	protected boolean isEditable(String path) throws Exception {
		
		return find(path).isEnabled();
	}
	
	protected boolean isVisible(String path) throws Exception {
		
		return find(path).isDisplayed();
	}
	
	protected void pause(long pauseTime) throws Exception {
		
		Thread.sleep(pauseTime);
	}
	
	protected void writeScreenshot(String path) throws Exception {

		FileUtils.copyFile(getScreenshot(), new File(path));
	}
	
	private File getScreenshot() throws Exception {
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		return scrFile;
	}
	
	protected WebElement find(String path) throws Exception {
		
		By by;
		
		String array[] = path.split("=");
		
		switch (array[0]) {
		
			case "id" : 	by = By.id(array[1]);
							break;
			
			case "name" : 	by = By.name(array[1]);
							break;
							
			case "link" :	by = By.linkText(array[1]);
							break;
							
			case "css"  : 	by = By.cssSelector(array[1]);
							break;
							
			default : throw new Exception("invalid search type");
		}
		
		
		return driver.findElement(by);
	}
	
	protected void click(String path) throws Exception {
		
		find(path).click();
	}
	
	protected void type(String path, String content) throws Exception {
		
		find(path).sendKeys(content);
	}
	
	protected void open(String url) {
		
		driver.get(url);
		
	}
	
	protected void openLinkNewTab(String linkText) {
		
		System.out.println(linkText);
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); 
		driver.findElement(By.linkText(linkText)).sendKeys(selectLinkOpeninNewTab);
	}
	
	protected void close() {
		
		driver.close();
	}
	
	protected void closeAll() {
		
		ArrayList<String> handles = new ArrayList<String> (driver.getWindowHandles());
		
		for(String handle : handles) {
		
			driver.switchTo().window(handle);
			driver.close();
		}
		
	}
	
	protected void clickAndWait(WebElement webElement) throws Exception {
		
		webElement.click();
		
		waitForPageToLoad();
	}
	
	protected void waitForPageToLoad() throws Exception {
	
	    //new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
	    //        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
		
		//WebDriverWait wait = new WebDriverWait(driver, 10);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("gridContainer")));
		
		Thread.sleep(2000);
	}
	
	protected void setupRemoteWebDriver() throws Exception {
		
		String browser = getProperty(BROWSER_NAME).toLowerCase();
		
		DesiredCapabilities capabilities = null;
		
		switch (browser) {
		
			case "ie" :  	 capabilities = DesiredCapabilities.internetExplorer();
					  		 break;
					  
			case "crhome" :  capabilities = DesiredCapabilities.chrome();
							 break;
							 
			case "safari" :  capabilities = DesiredCapabilities.safari();
							 break;
							 
			default : 		 capabilities = DesiredCapabilities.firefox();
							 break;
		}
		
		capabilities.setCapability("platform", getProperty(PLATFORM));
		capabilities.setCapability("version",  getProperty(VERSION));
	 
	    driver = new RemoteWebDriver(new URL(getProperty(SAUCE_LABS_URL)), capabilities);
	    
	    baseUrl = getProperty(URL);
	}
	
	protected void setupWebDriver(String browserName, String binaryPath, String url, String geckoDriverPath, String implicitlyWait) {
		
		if(browserName.equalsIgnoreCase("ie")) {
			
			driver = new InternetExplorerDriver(); 
			
		} else if(browserName.equalsIgnoreCase("chrome")) {
			
			if(binaryPath != null) {

				System.setProperty("webdriver.chrome.driver", binaryPath);
			}
			
			System.setProperty("webdriver.chrome.driver", geckoDriverPath);
			driver = new ChromeDriver();
			
		} else if (browserName.equalsIgnoreCase("safari")) {
			
			driver = new SafariDriver();
			
		} else {
			
			if(geckoDriverPath != null) {
				
				ProfilesIni profilesIni = new ProfilesIni();	
				FirefoxProfile fp = profilesIni.getProfile("selenium");
				DesiredCapabilities desiredCapability = DesiredCapabilities.firefox();
			    
				System.out.println("geckopath set to (" + geckoDriverPath + ")");
				System.setProperty("webdriver.gecko.driver", geckoDriverPath);
			    
			    desiredCapability.setCapability(FirefoxDriver.PROFILE, fp);
				
			    if(binaryPath != null) {
			    	
			    	driver = new FirefoxDriver(new FirefoxBinary(new File(binaryPath)), fp, desiredCapability);
			    	
			    } else {
			    
			    	driver = new FirefoxDriver(desiredCapability);
			    }
				
			} else {
				
				driver = new FirefoxDriver();
			}

			
		}
		
		baseUrl = url;
		
		if(implicitlyWait != null) {
		
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(implicitlyWait), TimeUnit.SECONDS);
			
		} else {
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		
		
		
		actions = new Actions(driver);
	}
}
