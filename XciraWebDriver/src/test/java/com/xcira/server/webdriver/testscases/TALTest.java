package com.xcira.server.webdriver.testscases;

import org.openqa.selenium.By;

import com.xcira.server.webdriver.WebDriverTestBase;

public class TALTest extends WebDriverTestBase {
	
	private long bidPause = 1000;
    private long confirmPause = 1000;
    private long textPause = 1000;
    
    private int i = 427;
	

	
	public void runTest(String browserName, String url) throws Exception {
		
		setupWebDriver(browserName, getProperty("BROWSER_BINARY_PATH") ,url, getProperty("GECKO_DRIVER_PATH"), getProperty(IMPLICITE_WAIT));
		
		open(baseUrl);
		
		while(!isDone()) {
			
			log(true, "open browser");
			
			find("id=userName").clear();
			type("id=userName", getProperty("USER_NAME"));
			type("id=password", getProperty("USER_NAME"));

			click("name=signin");
			
			if(isElementPresent("id=btnYes")) {
				
				log(true, "Yes button present");
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
				
			   log(true, "Biddding for user Id " + getProperty("USER_NAME"));
			   
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
			
			log(true, "logging out user");
			
			click("link=My Selections");

			click("css=input.signin_out_btn");
		}
		
		driver.close();
	}
	
	public boolean isDone() {
		
		return false;
		
	}
	
	public static void main(String args[]) throws Exception {
		
		TALTest talTest = new TALTest();
		
		if(args.length != 0) {
			
			talTest.loadProperties(args[0]);
			
		} else {
			
			System.out.println("Missing required argument: config.properties path");
			System.exit(-1);
		}
		
		talTest.openReport("TALTest");
				
		talTest.runTest(talTest.getProperty(BROWSER_NAME), talTest.getProperty(URL));
		
		talTest.closeReport();
	}

	private void closeReport() throws Exception {
		
		writeReport();
	}
}
