package com.xcira.server.webdriver.testscases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DemoTest extends FloorPlanningBase {
	
	private boolean reportOpened = false;
	
	@Before
	public void initilize() throws Exception {
	
		if(!reportOpened) {
			
			loadProperties("/home/shannodw/config.properties");
			openReport("DemoTest");
		}
	}
	
	@Test
	public void testMenuItems() throws Exception {
	
			try {
			
				setupWebDriver(getProperty(BROWSER_NAME), getProperty(BROWSER_PATH), getProperty(URL), getProperty(GECKO_PATH), "10");
				
				open(getProperty(URL));
				
				loginFloorplanning("ro@ro.com", "ro");
				
				getMenuItem("Dealers").click();
				
				find("link=Dealers List");
				getMenuItem("Vehicles").click();
				
				log(true, "clicked Vehicles Menu");
				getMenuItem("Reports").click();
				log(true, "clicked Reports Menu");
				getMenuItem("System").click();
				log(true, "clicked System Menu");
				getMenuItem("Help").click();
				log(true, "clicked Help Menu");
				
				writeScreenshot(getProperty(SCREENSHOT_DIR) + "/" + "testMenuItem.png");
				close();
				
			} catch (Exception e) {
				
				log(false, "testMenuItems");
			
				close();
				throw e;
			}
	}
	
	@After
	public void teardown() throws Exception {
		
		writeReport();
	}
	
	public void main(String args[]) {
		
		DemoTest test = new DemoTest();
		
		try {
		
			test.testMenuItems();
			
		} catch (Exception e) {

			if(e.getClass().getName().endsWith("FileNotFoundException")) {
			
				System.out.println("Please specify a valid config.properties file");
			} else {
				
				System.out.println("Some other error happened: " + e.getMessage());
			}
		}
		
	}

}
