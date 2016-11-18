package com.xcira.server.webdriver;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;

import com.xcira.report.LineItem;
import com.xcira.report.Report;

public class TALTest extends WebDriverTestBase {
	
	private Report  report; 
	private long bidPause = 1000;
    private long confirmPause = 1000;
    private long textPause = 1000;
    
    private int i = 427;
    
	static private Properties prop = new Properties();
	static private InputStream input = null;
	
	public void openReport() {
		
		report = new Report(getProperty("OUTPUT_FILE"));
	}
	
	public void runTest(String browserName, String url) throws Exception {
		
		setupWebDriver(browserName, url, getProperty("GECKO_DRIVER_PATH"), 10);
		
		open(baseUrl);
		
	    
		while(!isDone()) {
			
			report.putRecord(new LineItem(true, "open browser", "TALTest"));
			
			find("id=userName").clear();
			type("id=userName", getProperty("USER_NAME"));
			type("id=password", getProperty("USER_NAME"));

			click("name=signin");
			
			if(isElementPresent("id=btnYes")) {
				
				report.putRecord(new LineItem(true, "Yes button present", "TALTest"));
				click("id=btnYes");
			}
			
			if(isElementPresent(By.id("btnCancel"))) {
				
				click("id=btnCancel");
			} 
			
			click("id=lotNo");
			type("id=lotNo", "5430");
			type("id=endLotNo", "5439");
			
			click("id=lotSearch");

			pause(textPause);


			if(isEditable("id=quickBid" + i)) {
				
			       click("id=quickBid" + i);
			 
			       pause(bidPause);

			       if(isElementPresent("id=btnConfirmOk")) {
			           click("id=btnConfirmOk");
			           
			           pause(bidPause);
			           if(isElementPresent("id=btnBidCancel") && isVisible("id=btnBidCancel")) {
			               click("id=btnBidCancel");    
			           }
			       }
			}

			while (++i<=436)
			{
				
			   report.putRecord(new LineItem(true, "Biddding for user Id " + getProperty("USER_NAME"), "TALTest"));
			   
			   if(isEditable("id=quickBid" + i)) {
			       
			       click("id=quickBid" + i);
			 
			       pause(confirmPause);
	
			       if(isElementPresent("id=btnConfirmOk")) {
			           click("id=btnConfirmOk");
			           
			           pause(bidPause);
			           if(isElementPresent("id=btnBidCancel") && isVisible("id=btnBidCancel")) {
			        	   
			               click("id=btnBidCancel");    
			           }
			       }
			   }
			} 
			
			report.putRecord(new LineItem(true, "logging out user", "TALTest"));
			
			click("link=My Selections");

			click("css=input.signin_out_btn");
		}
		
		driver.close();
	}
	
	public boolean isDone() {
		
		return false;
		
	}
	
	public void loadProperties(String propertiesFileName) {
		
		try {

			input = new FileInputStream(propertiesFileName);
			
			prop.load(input);
		} catch (Exception e) {
			
			System.out.println("unable to read properties.");
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}
	
	public String getProperty(String key) {
		
		return prop.getProperty(key);
	}
	
	public static void main(String args[]) throws Exception {
		
		TALTest talTest = new TALTest();
		
		talTest.openReport();
		
		if(args.length != 0) {
			
			talTest.loadProperties(args[0]);
		} else {
			
			System.out.println("Missing required argument: config.properties path");
			System.exit(-1);
		}
				
		talTest.runTest(talTest.getProperty("BROWSER_NAME"), talTest.getProperty("URL"));
		
		talTest.closeReport();
	}

	private void closeReport() throws Exception {
		
		report.writeReport();
		
	}

}
