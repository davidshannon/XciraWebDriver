package com.xcira.report;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Report {

	private String fileName;
	private FileWriter fileWriter = null;
	private List<LineItem> reportItems = createList();
	
	public Report(String filename) {
		
		setFileName(filename);
	}
	
	public void putRecord(LineItem lineItem) {
	
		reportItems.add(lineItem);
	}
	
	private static <T> List<T> createList() {

        return new ArrayList<T>();
    }

	public void writeReport() throws Exception {
		
		fileWriter = new FileWriter(fileName);
		
		for(LineItem lineItem : reportItems) {
			
			fileWriter.write(lineItem.getStepName() + ", " + lineItem.isPass() + "," + lineItem.getTestCaseName() + "\n");
		}
	
		fileWriter.close();	
	}

	public String getFileName() {
		
		return fileName;
	}

	public void setFileName(String fileName) {
		
		this.fileName = fileName;
	}
}
