package com.xcira.server.webdriver.testscases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.xcira.sdk.exception.XciraException;
import com.xcira.server.webdriver.WebDriverTestBase;
import com.xcira.utils.CollectionUtil;

public class FloorPlanningBase extends WebDriverTestBase {

	private static int REQUIRED_FIELDS_PROSPECT = 5;
	private static String requiredProspectFields = "name fields.accountNumber fields.email password confirmPassword ";
	private static String requiredActiveApprovedFields = "name fields.accountNumber creditLimit fields.address fields.city fields.state fields.zip fields.attention fields.phone fields.email fields.email password confirmPassword fields.primaryName fields.primaryRole fields.primaryPhone fields.primaryEmail fields.ucc1State fields.mostRecentBusinessTaxYear fields.personalTaxYear ";

	private static final int GEN_QUOTE_DUE_DATE_INDEX=0;
	private static final int GEN_QUOTE_PAYMENT_DATE_INDEX=1;
	//private static final int GEN_QUOTE_BANKING_DATE_INDEX=2;
	
	private static List<String> yearMakeModel = CollectionUtil.createList();
	private static Map<String, String> loanDatesByYearMakeModel = CollectionUtil.createMap();
	
	private static final String vehicleInfoValidationTable[][][] = {
			{
				{
					"unexpected VIN label in drawer", "VIN" 
				},
				{
					"unexpected VIN in drawer", "1MELM5049PA644116"
				}
			},
			{
				{
					"Unexpected Loan Date label", "Loan Date"
				},
				{
					"Unexpected Loan Date value", "-3"
				}
			}, 
			{
				{
					"Unexpected Days Interest label", "Days Interest"
				},
				{
					"Unexpected Days Interest value", "91"
				}
			},
			{
				{
					"Unexpected Days on Account label", "Days On Account"
				},
				{
					"Unexpected Days on Account value", "91"
				}
			},
			{
				{
					"Unexpected Rate Program label", "Rate Program"
				},
				{
					"Unexpected Rate Program value", "Standard Rate 7.75%"
				}
			},
			{
				{
					"Unexpected Fee Schedule label", "Fee Schedule"
				},
				{
					"Unexpected Fee Schedule value", "$100 Fee"
				}
			}
	};

	protected WebElement verifyAttributeValues(By by, Map<String,String> attributeValues) {
		
		WebElement element = driver.findElement(by);
		
		for(String key : attributeValues.keySet()) {
		
			assertEquals(attributeValues.get(key), element.findElement(By.name("creditLimit")).getAttribute(key));
		}
		
		return element.findElement(By.name("creditLimit"));
	}
	
	protected List<WebElement> getPageOptionButtons() {
		
		return driver.findElement(By.cssSelector("div.pageOptions")).findElements(By.tagName("button"));
	}
	
	protected void clickPageOptionButton(String buttonText) {
		
		for(WebElement button : pageOptionButtons) {
			
			if(button.getText().equals(buttonText)) {
			
				actions.click(button).perform();
			}
		}
	}
	
	protected Map<String,By> loadLocators() throws Exception {
		
		Map <String, By> locationMap = CollectionUtil.createMap();
		
		
		By.cssSelector("button.infoVehicle");
		
		return locationMap;
	}
	
	protected WebElement getBreadcrumbLink(String linkText) throws Exception {
		
		return getBreadcrumbList().findElement(By.linkText(linkText));
	}
	
	protected WebElement getBreadcrumbList() throws Exception {
		
		return driver.findElement(By.cssSelector("ul.breadcrumb"));
	}
	
	protected List<WebElement> getDateTimePickers() throws Exception {
		
		return driver.findElements(By.xpath("//input[starts-with(@id,'dijit_form_DateTextBox')]"));
	}
	
	protected String getQuoteLineFieldValue(WebElement quoteLine, int field) throws Exception {
		
		return quoteLine.findElements(By.tagName("div")).get(field).getText();
	}
	
	protected WebElement getMenuItem(String menuName) throws Exception {
	
		String xpath = "//li[@id='widgets/display/menu/MenuItem_%xx%']/div";
		
		switch(menuName) {
		
			case "Dealers" : 	xpath = xpath.replace("%xx%", "0");
							 	break;
							 
			case "Vehicles" : 	xpath = xpath.replace("%xx%", "1");
							  	break;
							  
			case "Reports" : 	xpath = xpath.replace("%xx%", "2");
							 	break;
							 
			case "System" : 	xpath = xpath.replace("%xx%", "3");
			 				 	break;
			 			
			case "Help" : 		xpath = xpath.replace("%xx%", "4");
			 					break;
			 					
			default : throw new XciraException("Invalid Menu Item Selected");
		}
		
		return driver.findElement(By.xpath(xpath));
	}
	
	protected void addVehicle(String vin, String make, String model, String year, String color, String mileage, String amount, int loanDateOffset) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, loanDateOffset);
		String loanDate = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
		calendar.add(Calendar.MONTH, +1);
		String dueDate = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
		
		driver.findElement(By.cssSelector("button.addVehicle")).click();
		
		List<WebElement> dateTimePickerElements = driver.findElements(By.xpath("//input[starts-with(@id,'dijit_form_DateTextBox')]"));
		
		dateTimePickerElements.get(0).clear();
		dateTimePickerElements.get(0).sendKeys(loanDate);
		
		dateTimePickerElements.get(1).clear();
		dateTimePickerElements.get(1).sendKeys(loanDate);
		
		dateTimePickerElements.get(3).clear();
		dateTimePickerElements.get(3).sendKeys(dueDate);


		driver.findElement(By.name("fields.vin")).clear();
		driver.findElement(By.name("fields.vin")).sendKeys(vin);
		
		if (isElementPresent(By.cssSelector("button.alertButton"))) {
			
			driver.findElement(By.cssSelector("button.alertButton")).click();
		}
		
		driver.findElement(By.name("fields.make")).sendKeys(make);
		driver.findElement(By.name("fields.model")).sendKeys(model);
		driver.findElement(By.name("fields.year")).sendKeys(year);
		driver.findElement(By.name("fields.color")).sendKeys(color);
		driver.findElement(By.name("fields.mileage")).sendKeys(mileage);
		driver.findElement(By.name("amount")).sendKeys(amount);
		
		driver.findElement(By.cssSelector("button.addBtn")).click();
		
		assertEquals("You Have Successfully Created a Vehicle",driver.findElement(By.cssSelector(".alertMessage")).getText());
		actions.click(driver.findElement(By.cssSelector("button.alertButton"))).perform();
		
		yearMakeModel.add(year + " " + make + " " + model);
		loanDatesByYearMakeModel.put(year + " " + make + " " + model, loanDate);
	}
	
	/*private void makeVehicleList(String vin, String make, String model, String year, String color, String mileage, String amount, int loanDateOffset) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, loanDateOffset);
		String loanDate = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
		
		yearMakeModel.add(year + " " + make + " " + model);
		loanDatesByYearMakeModel.put(year + " " + make + " " + model, loanDate);
	}*/

	protected void verifyRequiredFields() throws Exception{

		List<WebElement> requiredElements = driver.findElements(By.className("required"));

		assertEquals("unexpected required fields: ", REQUIRED_FIELDS_PROSPECT, requiredElements.size());

		String foundElements = "";
		for (WebElement element : requiredElements) {

			for (String fieldName : requiredProspectFields.split(" ")) {

				if (element.getAttribute("name").equals(fieldName)) {

					foundElements += element.getAttribute("name") + " ";
				}
			}
		}
		
		assertEquals("prospect unexpected required fields:", requiredProspectFields, foundElements);

		new Select(driver.findElement(By.name("fields.status"))).selectByVisibleText("Active/Approved");

		requiredElements = driver.findElements(By.className("required"));
		
		String foundActiveApprovedElements = "";
		for (WebElement element : requiredElements) {
;
			for (String fieldName : requiredActiveApprovedFields.split(" ")) {

				if (element.getAttribute("name").equals(fieldName)) {

					foundActiveApprovedElements += element.getAttribute("name") + " ";
				}
			}
		}
		
		//To Do: fix this so it works even if the order ends up being different between runs.
		assertEquals("prospect unexpected Active/Approved required fields:", requiredActiveApprovedFields, foundActiveApprovedElements);
	}

	protected void loginFloorplanning(String email, String password) throws Exception {
		
		driver.get(baseUrl);
		find("name=email").clear();
		find("name=email").sendKeys(email);
		find("name=password").clear();
		find("name=password").sendKeys(password);
		find("css=button.signInButton").click();
		
		waitForPageToLoad();
	}
	
	protected void goToDealerDetailPage(String dealerName) throws Exception {
		
		clickAndWait(getMenuItem("Dealers"));
		
	    actions.click(driver.findElement(By.cssSelector("span.dgrid-last.dgrid-page-link"))).perform();
	    
	    if(!isElementPresent(By.xpath("//td[contains(text(),'" + dealerName + "')]/preceding-sibling::td"))) {
	    
	    	actions.click(driver.findElement(By.cssSelector("span.dgrid-previous.dgrid-page-link"))).perform();
	    }
	    
	    actions.click(driver.findElement(By.xpath("//td[contains(text(),'" + dealerName + "')]/preceding-sibling::td"))).perform();
	    
	}
	
	protected void goToGenerateQuotePage(String dealerName, int index) throws Exception {
		
		actions.click(driver.findElement(By.xpath("//div[contains(text(),'Dealers')]"))).perform();
	    actions.click(driver.findElement(By.cssSelector("span.dgrid-last.dgrid-page-link"))).perform();
	    
	    if(!isElementPresent(By.xpath("//td[contains(text(),'" + dealerName + "')]/preceding-sibling::td"))) {
		    
	    	actions.click(driver.findElement(By.cssSelector("span.dgrid-previous.dgrid-page-link"))).perform();
	    }
	    
	    actions.click(driver.findElement(By.xpath("//td[contains(text(),'" + dealerName + "')]/preceding-sibling::td"))).perform();
	    
	    generateQuoteForVehicle(index);
	    
	}
	
	protected void generateQuoteForVehicle(int index) throws Exception {
		
	    List<WebElement> fieldSelects = driver.findElements(By.cssSelector("td.field-select"));
	    
	    assertTrue(index < fieldSelects.size());
	    
	    actions.click(fieldSelects.get(index)).perform();
	    
	    actions.click(driver.findElement(By.cssSelector("button.quote"))).perform();

	}
	
	/*private void generateQuoteForVehicles(List<Integer> indexes) throws Exception {
		
	    List<WebElement> fieldSelects = driver.findElements(By.cssSelector("td.field-select"));
	    
	    for(int index : indexes) {
	    	
	    	actions.click(fieldSelects.get(index)).perform();
	    }
	    
	    actions.click(driver.findElement(By.cssSelector("button.quote"))).perform();

	}*/
	
	protected String convertDateToLabelFormat(String dateString) throws Exception{
		
		SimpleDateFormat fromFormat = new SimpleDateFormat("MM/dd/yyyy");
		fromFormat.setLenient(false);
		SimpleDateFormat toFormat = new SimpleDateFormat("MMM d, yyyy");
		toFormat.setLenient(false);
		
		return toFormat.format(fromFormat.parse(dateString));
	}
	
	protected void validateVehicalQuotePage(WebElement quoteLine, String daysInterest, String daysOnAccount, int vehicleIndex) throws Exception {
		
		quoteLine.findElement(By.cssSelector("button.infoVehicle")).click();
		
		Thread.sleep(100);
		
		WebElement vehicleInfoDrawer = driver.findElement(By.cssSelector("div.vehicleInfo"));
		
		assertEquals("unexpected asset name value: ", yearMakeModel.get(vehicleIndex), vehicleInfoDrawer.findElement(By.cssSelector("div.assetName")).getText());
		
		List<WebElement> vehicleLiElements = vehicleInfoDrawer.findElements(By.tagName("li")); 
		
		for(int i=0; i < vehicleInfoValidationTable.length; i++) {
		
			List<WebElement> divs = vehicleLiElements.get(i).findElements(By.tagName("div"));
			
			assertEquals("unexpected number of divs in vehicle info <li>", 2, divs.size());
			
			assertEquals(vehicleInfoValidationTable[i][0][0],vehicleInfoValidationTable[i][0][1],divs.get(0).getText());
			
			switch (vehicleInfoValidationTable[i][0][1]) {

				case "Loan Date" : 		 assertEquals(vehicleInfoValidationTable[i][1][0], convertDateToLabelFormat(loanDatesByYearMakeModel.get(yearMakeModel.get(vehicleIndex))), divs.get(1).getText());
					               		 break;
			
				case "Days Interest" : 	 assertEquals(vehicleInfoValidationTable[i][1][0],daysInterest, divs.get(1).getText());
										 break;
				
				case "Days On Account" : assertEquals(vehicleInfoValidationTable[i][1][0],daysOnAccount, divs.get(1).getText());
										 break;
										 
				default :				 assertEquals(vehicleInfoValidationTable[i][1][0],vehicleInfoValidationTable[i][1][1],divs.get(1).getText()); 				 
			}
		}
	}
	
	protected Date updateDate(Date date, int field, int days) {
		
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(field, days);
	    
	    return cal.getTime();
	}
	
	protected void clickAlertOk() throws Exception {
		
		driver.findElement(By.xpath("//button[contains(text(), 'Ok')]")).click();
	}
	
	protected void setPaymentDateOnQuotePage(Date date) throws Exception {
	
		setQuoteDate(date, GEN_QUOTE_PAYMENT_DATE_INDEX);
	}
	
	protected void setBankingDateOnQuotePage(Date date) throws Exception {
		
		setQuoteDate(date, GEN_QUOTE_PAYMENT_DATE_INDEX);
	}

	protected void setDueDateOnQuotePage(Date date) throws Exception {
		
		setQuoteDate(date, GEN_QUOTE_DUE_DATE_INDEX);
	}
	
	protected void setQuoteDate(Date date, int index) throws Exception {
		
		List<WebElement> dateTimePickers = getDateTimePickers();
		
		assertEquals("Unexpected number of date time picker elements on quote payment page", 3, dateTimePickers.size());

		dateTimePickers.get(index).clear();
		dateTimePickers.get(index).sendKeys(new SimpleDateFormat("MM/dd/yyyy").format(date));		
	}
	
	protected int daysBetween(Date d1, Date d2)
	{
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}
