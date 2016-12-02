package com.xcira.server.webdriver;

import java.io.FileInputStream;
import java.util.Properties;

public class TestBase {
	
	static private Properties properties = new Properties();
	
	
	protected void loadProperties(String propertiesFileName) throws Exception {
		

		FileInputStream input = new FileInputStream(propertiesFileName);
			
		properties.load(input);
	}
	
	protected String getProperty(String key) {
		
		return properties.getProperty(key);
	}
}
