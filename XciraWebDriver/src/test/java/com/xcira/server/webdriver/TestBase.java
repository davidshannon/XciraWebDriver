package com.xcira.server.webdriver;

import java.io.FileInputStream;
import java.util.Properties;

import com.xcira.report.LineItem;
import com.xcira.report.Report;

public class TestBase {
	
	private String testcaseName;
	private Report report; 
	static private Properties properties = new Properties();
	
	
	public void openReport(String testcaseName) {
		
		this.testcaseName = testcaseName;
		
		report = new Report(getProperty("OUTPUT_FILE"));
	}
	
	protected void log(boolean passed, String stepName) {
		
		report.putRecord(new LineItem(passed, stepName, testcaseName));
	}
	
	protected void writeReport() throws Exception{
	
		report.writeReport();
	}
	
	protected void loadProperties(String propertiesFileName) throws Exception {
		

		FileInputStream input = new FileInputStream(propertiesFileName);
			
		properties.load(input);
	}
	
	protected String getProperty(String key) {
		
		return properties.getProperty(key);
	}
}
