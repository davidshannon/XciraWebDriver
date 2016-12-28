package com.xcira.server.webdriver.testscases;

import java.util.ArrayList;

import com.xcira.server.webdriver.WebDriverTestBase;

public class GARLSimpleLoadTest extends WebDriverTestBase {
	
	
	public void runTest(String browserName, String url) throws Exception {
		
		String linkText = "#X:GARL CO LOT X";
		
		int numBidders = Integer.parseInt(getProperty("NUMBIDDERS"));
		
		String bidderIds[] = new String[numBidders];
		int firstId = Integer.parseInt(getProperty("STARTBIDDERID"));
		
		for(int i=0; i<bidderIds.length; i++) {
			
			int id = firstId+i;
			
			bidderIds[i] = getProperty("USERNAME") + id;
		}
		
		for(int j=0; j<50; j++) {
		
			try { 
				
				if(getProperty(SAUCE_LABS_URL) != null) {
					
					setupRemoteWebDriver();
					
				} else {
				
					setupWebDriver(browserName, getProperty(BROWSER_PATH) ,url, getProperty(GECKO_PATH), getProperty(IMPLICITE_WAIT));
				}
				
				open(baseUrl);
				
				find("id=USERNAME").clear();
				type("id=USERNAME", bidderIds[j%numBidders]);
				type("id=PASSWORD", getProperty("PASSWORD"));
		
				click("id=btn_login");
				
				Thread.sleep(1000);
				
				click("id=viewList10000");
				
				Thread.sleep(10000);
				
				for(int i=1; i<=5; i++) {
					
					openLinkNewTab(linkText.replaceAll("X", Integer.toString(i)));
				}
				
				Thread.sleep(5000);
				
				ArrayList<String> handles = new ArrayList<String> (driver.getWindowHandles());
				
				if(handles.size() < 6) {
					
					System.out.println("Unable to continue incorrect number of tabs open");
					System.exit(-1);
				}
				
				for(int i = 1; i <= 5; i++) {
					
					driver.switchTo().window(handles.get(i));
		
					Thread.sleep(3000);
					click("id=dijit_form_Button_1");
					
					Thread.sleep(2000);
					
					click("id=dijit_form_Button_6_label");
					
					Thread.sleep(2000);
					
					click("link=Next");
				}
				
				Thread.sleep(5000);
				
				click("link=Logout"); 
				
				Thread.sleep(1000);
				
				click("id=confirmOK");
			} catch (Exception e) {
				
				continue;
			}
			
			driver.quit();
			
		}
	}
	
	public boolean isDone() {
		
		return false;
		
	}
	
	public static void main(String args[]) throws Exception {
		
		GARLSimpleLoadTest garlTest = new GARLSimpleLoadTest();
		
		if(args.length != 0) {
			
			garlTest.loadProperties(args[0]);
			
		} else {
			
			System.out.println("Missing required argument: config.properties path");
			System.exit(-1);
		}
		
		garlTest.openReport("GARL");
				
		garlTest.runTest(garlTest.getProperty(BROWSER_NAME), garlTest.getProperty(URL));
		
		garlTest.closeReport();
	}

	private void closeReport() throws Exception {
		
		writeReport();
	}

}
