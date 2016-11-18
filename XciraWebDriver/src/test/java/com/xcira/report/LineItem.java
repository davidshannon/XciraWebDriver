package com.xcira.report;

public class LineItem {
	
	private String testCaseName;
	private String stepName;
	private boolean pass;
	
	
	public LineItem(boolean b, String stepName, String testCaseName) {
		
		setPass(b);
		setStepName(stepName);
		setTestCaseName(testCaseName);
	}
	
	public String getTestCaseName() {
	
		return testCaseName;
	}
	
	public void setTestCaseName(String testCaseName) {
		
		this.testCaseName = testCaseName;
	}

	public String getStepName() {
		
		return stepName;
	}

	public void setStepName(String stepName) {
		
		this.stepName = stepName;
	}

	public boolean isPass() {
		
		return pass;
	}

	public void setPass(boolean pass) {
		
		this.pass = pass;
	}
}
