package com.xcira.server.webdriver.testscases;

import java.util.ArrayList;

import com.xcira.server.webdriver.WebDriverTestBase;

public class GARLSimpleLoadTest extends WebDriverTestBase {
	
	private int numTabs = 0;
	
	public void runTest(String browserName, String url) throws Exception {
		
		String linkText = getProperty("LINKTEXT_TEMPLATE");
		
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
				
				click("id=viewList" + getProperty("EVENTID"));
				
				Thread.sleep(10000);
				
				for(int i=1; i<= getNumTabs(); i++) {
					
					openLinkNewTab(linkText.replaceAll("%X%", Integer.toString(i)));
				}
				
				Thread.sleep(10000);
				
				String oldTab = driver.getWindowHandle();
				
				ArrayList<String> handles = new ArrayList<String> (driver.getWindowHandles());
				
				handles.remove(oldTab);
				
				if(handles.size() < getNumTabs()) {
					
					System.out.println("Unable to continue incorrect number of tabs open");
					System.exit(-1);
				}
				
				for(int i = 0; i < getNumTabs(); i++) {
					
					//This is where chrome fails.   For some reason switching to the tab gives you an element not clickable
					//at point (563, 146) error when you try to do the switch.   Firefox doesn't run into this issue.  Not
					//Sure what the deal is with this on the chromedriver.  If you let this run on chrome the error catch
					//will print out the entire error message.  Perhaps someone in the future can figure out why this fails on
					//chrome.
					System.out.println(handles.get(i));
					driver.switchTo().window(handles.get(i));
		
					Thread.sleep(3000);
					click("id=dijit_form_Button_1");
					
					Thread.sleep(2000);
					
					click("id=dijit_form_Button_6_label");
					
					Thread.sleep(2000);
					
					click("link=Next");
				}
				
				driver.switchTo().window(oldTab); 
				
				Thread.sleep(5000);
				
				click("link=Logout"); 
				
				Thread.sleep(1000);
				
				click("id=confirmOK");
			
			} catch (Exception e) {
				
				System.out.println(e.getMessage());
				continue;
				
			} finally {
		
				driver.quit();
				
				if (browserName.equalsIgnoreCase("chrome")) {
					
					break;
				}
			}
		}
	}
	
	public boolean isDone() {
		
		return false;
		
	}
	
	public static void main(String args[]) throws Exception {
		
		GARLSimpleLoadTest garlTest = new GARLSimpleLoadTest();
		
		if(args.length != 0) {
			
			garlTest.loadProperties(args[0]);
			
			try {
				
				garlTest.setNumTabs(Integer.parseInt(garlTest.getProperty("NUMTABS")));
			} catch (NumberFormatException nfe) {
				
				System.out.println("Error reading NUMTABS property.");
				System.out.println(nfe.getLocalizedMessage());
				
				System.exit(-1);
			}
			
			
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

	public int getNumTabs() {
		return numTabs;
	}

	public void setNumTabs(int numTabs) {
		this.numTabs = numTabs;
	}

}
