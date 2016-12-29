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
		
		if(getProperty("OUTPUT_FILE") != null) {
		
			report = new Report(getProperty("OUTPUT_FILE"));
		}
	}
	
	protected void log(boolean passed, String stepName) {
		
		if(report != null) {
		
			report.putRecord(new LineItem(passed, stepName, testcaseName));
		}
		
	}
	
	protected void writeReport() throws Exception{
	
		if(report != null) {
		
			report.writeReport();
		}
		
	}
	
	protected void loadProperties(String propertiesFileName) throws Exception {
		

		FileInputStream input = new FileInputStream(propertiesFileName);
			
		properties.load(input);
	}
	
	protected String getProperty(String key) {
		
		return properties.getProperty(key);
	}
}
