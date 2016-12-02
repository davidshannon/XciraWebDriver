package com.xcira.server.webdriver.testscases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.xcira.server.webdriver.WebDriverTestBase;
import com.xcira.utils.CollectionUtil;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateDealer extends WebDriverTestBase {

	private static int REQUIRED_FIELDS_PROSPECT = 5;

	private static String requiredProspectFields = "name fields.accountNumber fields.email password confirmPassword ";
	private static String requiredActiveApprovedFields = "name fields.accountNumber creditLimit fields.address fields.city fields.state fields.zip fields.attention fields.phone fields.email fields.email password confirmPassword fields.primaryName fields.primaryRole fields.primaryPhone fields.primaryEmail fields.ucc1State fields.mostRecentBusinessTaxYear fields.personalTaxYear ";

	private static final int START_TIME = 0;
	private static final int LICENCE_EXPIRATION = 2;
	private static final int UCC1_EXPIRATION = 3;
	private static final int INSURANCE_EXPIRATION = 4;
	private static final int BOND_EXPIRATION = 5;
	private static final int RECENT_SIGNED_DOCS = 6;
	private static final int BUSINESS_FINAINCIALS = 7;
	private static final int PERSONAL_FINANCE = 8;
	
	private static final int GEN_QUOTE_DUE_DATE_INDEX=0;
	private static final int GEN_QUOTE_PAYMENT_DATE_INDEX=1;
	//private static final int GEN_QUOTE_BANKING_DATE_INDEX=2;
	
	//private static final int QUOTE_LINE_FIELD_INFO_ICON=0;
	//private static final int QUOTE_LINE_PAY_OFF_CURTAILMENT=1;
	private static final int QUOTE_LINE_LOAN_DATE=3;
	//private static final int QUOTE_LINE_LOAN_AMOUNT=4;
	//private static final int QUOTE_LINE_PRINCIPLE_BALANCE=5;
	//private static final int QUOTE_LINE_VIN=6;
	//private static final int QUOTE_LINE_YEAR_MAKE_MODEL=8;
	//private static final int QUOTE_LINE_PRINCIPLE=9;
	//private static final int QUOTE_LINE_INTEREST=10;
	//private static final int QUOTE_LINE_ADMIN_FEE=11;
	//private static final int QUOTE_LINE_ADJUSTMENT=12;
	//private static final int QUOTE_LINE_ADJUSTMENT_TYPE=13;
	private static final int QUOTE_LINE_DUE_DATE=14;
	//private static final int QUOTE_LINE_PAYMENT_AMOUNT=15;
	
	private static String dealerName;
	
	private static List<String> yearMakeModel = CollectionUtil.createList();
	private static Map<String, String> loanDatesByYearMakeModel = CollectionUtil.createMap();
	
	private static final double loanAmount[] = {
			15555.55,
			10000.00,
			25400.35,
			26500.66,
			30000.00
	};
	
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
					"Unexpected Days Interest value", "92"
				}
			},
			{
				{
					"Unexpected Days on Account label", "Days On Account"
				},
				{
					"Unexpected Days on Account value", "92"
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

	@BeforeClass
	public static void setUp() throws Exception {

		//driver = new FirefoxDriver();
		//baseUrl="http://ng.xcira.com";
		//driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		//actions = new Actions(driver);
	}

	@Test
	public void test1CreateDealer() throws Exception {

		Calendar calendar = Calendar.getInstance();
		String emailPrefix = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
		String today = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());

		calendar.add(Calendar.YEAR, 1);
		String nextYear = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());

		dealerName = "WebDriver_" + emailPrefix;
		
		loginFloorplanning("ro@ro.com", "ro");
		driver.findElement(By.xpath("//li[@id='widgets/display/menu/MenuItem_0']/div")).click();
		
		driver.findElement(By.cssSelector("button.add")).click();

		verifyRequiredFields();

		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys(dealerName);
		driver.findElement(By.name("fields.accountNumber")).clear();
		driver.findElement(By.name("fields.accountNumber")).sendKeys("AcctNum" + emailPrefix);
		new Select(driver.findElement(By.name("fields.status"))).selectByVisibleText("Active/Approved");

		List<WebElement> dateTimePickerElements = getDateTimePickers();

		dateTimePickerElements.get(START_TIME).sendKeys(today);
		assertEquals("Unexpected number of date time pickers", dateTimePickerElements.size(), 9);

		driver.findElement(By.cssSelector("button.addBtn.bgcolor")).click();
		assertEquals("Credit Line Cannot Be Empty", driver.findElement(By.cssSelector(".alertMessage")).getText());
		driver.findElement(By.cssSelector("button.alertButton")).click();

		driver.findElement(By.name("creditLimit")).clear();
		driver.findElement(By.name("creditLimit")).sendKeys("50000");
		driver.findElement(By.name("fields.address")).clear();
		driver.findElement(By.name("fields.address")).sendKeys("410 Random Street");
		driver.findElement(By.name("fields.city")).clear();
		driver.findElement(By.name("fields.city")).sendKeys("Tampa");
		driver.findElement(By.name("fields.state")).clear();
		driver.findElement(By.name("fields.state")).sendKeys("FL");
		driver.findElement(By.name("fields.zip")).clear();
		driver.findElement(By.name("fields.zip")).sendKeys("33510");
		driver.findElement(By.name("fields.attention")).clear();
		driver.findElement(By.name("fields.attention")).sendKeys("David");
		driver.findElement(By.name("fields.phone")).clear();
		driver.findElement(By.name("fields.phone")).sendKeys("555-555-5555");

		driver.findElement(By.name("fields.email")).clear();
		driver.findElement(By.name("fields.email")).sendKeys(emailPrefix + "@xcira.com");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("pw");
		driver.findElement(By.name("confirmPassword")).clear();
		driver.findElement(By.name("confirmPassword")).sendKeys("pw");
		
		new Select(driver.findElement(By.name("fields.defaultFeeId"))).selectByVisibleText("$100 Fee");
		new Select(driver.findElement(By.name("fields.defaultRateId"))).selectByVisibleText("Standard Rate 7.75%");
		new Select(driver.findElement(By.name("fields.curtailmentId"))).selectByVisibleText("10% curtailment");
		driver.findElement(By.name("fields.primaryName")).clear();
		driver.findElement(By.name("fields.primaryName")).sendKeys("David");
		driver.findElement(By.name("fields.primaryRole")).clear();
		driver.findElement(By.name("fields.primaryRole")).sendKeys("Manager");
		driver.findElement(By.name("fields.primaryPhone")).clear();
		driver.findElement(By.name("fields.primaryPhone")).sendKeys("555-555-5555");
		driver.findElement(By.name("fields.primaryEmail")).clear();
		driver.findElement(By.name("fields.primaryEmail")).sendKeys("dshannon@xcira.com");

		dateTimePickerElements.get(LICENCE_EXPIRATION).sendKeys(nextYear);
		dateTimePickerElements.get(UCC1_EXPIRATION).sendKeys(nextYear);
		dateTimePickerElements.get(INSURANCE_EXPIRATION).sendKeys(nextYear);
		dateTimePickerElements.get(BOND_EXPIRATION).sendKeys(nextYear);
		dateTimePickerElements.get(RECENT_SIGNED_DOCS).sendKeys(nextYear);
		dateTimePickerElements.get(BUSINESS_FINAINCIALS).sendKeys(nextYear); 
		dateTimePickerElements.get(PERSONAL_FINANCE).sendKeys(nextYear);

		driver.findElement(By.name("fields.ucc1State")).clear();
		driver.findElement(By.name("fields.ucc1State")).sendKeys("Florida");
		driver.findElement(By.name("fields.mostRecentBusinessTaxYear")).clear();
		driver.findElement(By.name("fields.mostRecentBusinessTaxYear")).sendKeys("2016");
		driver.findElement(By.name("fields.personalTaxYear")).clear();
		driver.findElement(By.name("fields.personalTaxYear")).sendKeys("2016");

		driver.findElement(By.cssSelector("button.addBtn.bgcolor")).click();

		assertEquals("You Have Successfully Created a Dealer",driver.findElement(By.cssSelector(".alertMessage")).getText());
		actions.click(driver.findElement(By.cssSelector("button.alertButton"))).perform();
	}
	
	@Test
	public void test2DealerDetails() throws Exception {
		
		goToDealerDetailPage(dealerName);
		
		assertEquals("Rosalie", driver.findElement(By.cssSelector("span.userName")).getText());
		
		pageOptionButtons = getPageOptionButtons();
		
		assertEquals("invalid page option button count: ", 6, pageOptionButtons.size());
		
		clickPageOptionButton("Print");

	}
	
	@Test
	public void test3VinLookup() throws Exception {
		
		//Calendar calendar = Calendar.getInstance();
		//Random rand = new Random();
		//String uniqueVin = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime()) + rand.nextInt(900) + 100;
		
		goToDealerDetailPage(dealerName);
		
		driver.findElement(By.cssSelector("button.addVehicle")).click();
		driver.findElement(By.name("fields.vin")).sendKeys("12345678901234567");
		assertEquals("Duplicate VIN", driver.findElement(By.cssSelector("div.alertTitle")).getText());
		driver.findElement(By.cssSelector("button.alertButton")).click();
		
		driver.findElement(By.name("fields.vin")).clear();
		driver.findElement(By.name("fields.vin")).sendKeys("WDDGF8BB3CF919444");
		
		WebElement vinLookupResult = driver.findElement(By.cssSelector("div.vinLookUpResult"));
		vinLookupResult.findElement(By.xpath("//td[contains(text(), 'WDDGF8BB3CF919444')]"));
		vinLookupResult.findElement(By.xpath("//td[contains(text(), 'Mercedes-Benz')]"));
		vinLookupResult.findElement(By.xpath("//td[contains(text(), 'C-Class')]"));
		vinLookupResult.findElement(By.xpath("//td[contains(text(), '2012')]"));
		
		vinLookupResult.click();
		
		assertEquals("Mercedes-Benz", driver.findElement(By.name("fields.make")).getAttribute("value"));
		assertEquals("C-Class", driver.findElement(By.name("fields.model")).getAttribute("value"));
		assertEquals("2012", driver.findElement(By.name("fields.year")).getAttribute("value"));
	}
	
	@Test
	public void testAddVehicle() throws Exception {
	
		goToDealerDetailPage(dealerName);
		
		for(int i = 0, dateOffset = 1; i < 5; i++, dateOffset++) {
			
			addVehicle("1MELM5049PA644116", "WdMake_" + i, "WdModel_" + i, "200" + i, "WdColor_" + i, Integer.toString(i*10000), Double.toString(loanAmount[i]), -dateOffset*3);
		}
	}
	
	@Test
	public void testGenerateQuotePage() throws Exception {
				
		goToGenerateQuotePage(dealerName, 0);

		WebElement saveQuoteButton = driver.findElement(By.cssSelector("button.compute"));
		WebElement quoteLine = driver.findElement(By.cssSelector("div.quoteLine"));		
		
		validateVehicalQuotePage(quoteLine, "92", "92", 0);
		
		SimpleDateFormat formalDateFormat = new SimpleDateFormat("MMM dd, yyyy");
				
		setPaymentDateOnQuotePage(formalDateFormat.parse(getQuoteLineFieldValue(quoteLine, QUOTE_LINE_DUE_DATE)));
		setBankingDateOnQuotePage(formalDateFormat.parse(getQuoteLineFieldValue(quoteLine, QUOTE_LINE_DUE_DATE)));
		
		int daysBetweenLoanAndPayment = daysBetween(formalDateFormat.parse(getQuoteLineFieldValue(quoteLine, QUOTE_LINE_LOAN_DATE)), formalDateFormat.parse(getQuoteLineFieldValue(quoteLine, QUOTE_LINE_DUE_DATE)));
		
		saveQuoteButton.click();
		
		clickAlertOk();
		
		getBreadcrumbLink("Dealer Quote").click();
		
		driver.findElement(By.cssSelector("button.viewQuote")).click();

		quoteLine = driver.findElement(By.cssSelector("div.quoteLine"));		
		
		
		validateVehicalQuotePage(quoteLine, Integer.toString(daysBetweenLoanAndPayment), Integer.toString(daysBetweenLoanAndPayment), 0);
		
		driver.findElement(By.cssSelector("button.payment")).click();
		
		setDueDateOnQuotePage(updateDate(formalDateFormat.parse(getQuoteLineFieldValue(quoteLine, QUOTE_LINE_DUE_DATE)), Calendar.MONTH, 1));
		
		driver.findElement(By.cssSelector("button.addBtn")).click();
		clickAlertOk();
		clickAlertOk();
		
		getBreadcrumbLink("Dealer Quote").click();
		
		driver.findElement(By.cssSelector("button.viewQuote")).click();
		
		assertEquals("The quote is already paid", driver.findElement(By.cssSelector("button.payment")).getAttribute("title"));
	}

	
	private String getQuoteLineFieldValue(WebElement quoteLine, int field) throws Exception {
		
		return quoteLine.findElements(By.tagName("div")).get(field).getText();
	}
	
	private void addVehicle(String vin, String make, String model, String year, String color, String mileage, String amount, int loanDateOffset) throws Exception {
		
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

	private void verifyRequiredFields() throws Exception{

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

			for (String fieldName : requiredActiveApprovedFields.split(" ")) {

				if (element.getAttribute("name").equals(fieldName)) {

					foundActiveApprovedElements += element.getAttribute("name") + " ";
				}
			}
		}
		
		assertEquals("prospect unexpected Active/Approved required fields:", requiredActiveApprovedFields, foundActiveApprovedElements);
	}
	
	private void loginFloorplanning(String email, String password) {
		
		driver.get(baseUrl);
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.signInButton")).click();
	}
	
	private void goToDealerDetailPage(String dealerName) {
		
		actions.click(driver.findElement(By.xpath("//div[contains(text(),'Dealers')]"))).perform();
	    actions.click(driver.findElement(By.cssSelector("span.dgrid-last.dgrid-page-link"))).perform();
	    
	    if(!isElementPresent(By.xpath("//td[contains(text(),'" + dealerName + "')]/preceding-sibling::td"))) {
	    
	    	actions.click(driver.findElement(By.cssSelector("span.dgrid-previous.dgrid-page-link"))).perform();
	    }
	    
	    actions.click(driver.findElement(By.xpath("//td[contains(text(),'" + dealerName + "')]/preceding-sibling::td"))).perform();
	    
	}
	
	private void goToGenerateQuotePage(String dealerName, int index) throws Exception {
		
		actions.click(driver.findElement(By.xpath("//div[contains(text(),'Dealers')]"))).perform();
	    actions.click(driver.findElement(By.cssSelector("span.dgrid-last.dgrid-page-link"))).perform();
	    
	    if(!isElementPresent(By.xpath("//td[contains(text(),'" + dealerName + "')]/preceding-sibling::td"))) {
		    
	    	actions.click(driver.findElement(By.cssSelector("span.dgrid-previous.dgrid-page-link"))).perform();
	    }
	    
	    actions.click(driver.findElement(By.xpath("//td[contains(text(),'" + dealerName + "')]/preceding-sibling::td"))).perform();
	    
	    generateQuoteForVehicle(index);
	    
	}
	
	private void generateQuoteForVehicle(int index) throws Exception {
		
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
	
	private String convertLoanDateToLabelFormat(String dateString) throws Exception{
		
		SimpleDateFormat fromFormat = new SimpleDateFormat("MM/dd/yyyy");
		fromFormat.setLenient(false);
		SimpleDateFormat toFormat = new SimpleDateFormat("MMM d, yyyy");
		toFormat.setLenient(false);
		
		return toFormat.format(fromFormat.parse(dateString));
	}
	
	private void validateVehicalQuotePage(WebElement quoteLine, String daysInterest, String daysOnAccount, int vehicleIndex) throws Exception {
		
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

				case "Loan Date" : 		 assertEquals(vehicleInfoValidationTable[i][1][0], convertLoanDateToLabelFormat(loanDatesByYearMakeModel.get(yearMakeModel.get(vehicleIndex))), divs.get(1).getText());
					               		 break;
			
				case "Days Interest" : 	 assertEquals(vehicleInfoValidationTable[i][1][0],daysInterest, divs.get(1).getText());
										 break;
				
				case "Days On Account" : assertEquals(vehicleInfoValidationTable[i][1][0],daysOnAccount, divs.get(1).getText());
										 break;
										 
				default :				 assertEquals(vehicleInfoValidationTable[i][1][0],vehicleInfoValidationTable[i][1][1],divs.get(1).getText()); 				 
			}
		}
	}
	
	private List<WebElement> getDateTimePickers() throws Exception {
		
		return driver.findElements(By.xpath("//input[starts-with(@id,'dijit_form_DateTextBox')]"));
	}
	
	private WebElement getBreadcrumbLink(String linkText) throws Exception {
		
		return getBreadcrumbList().findElement(By.linkText(linkText));
	}
	private WebElement getBreadcrumbList() throws Exception {
		
		return driver.findElement(By.cssSelector("ul.breadcrumb"));
	}
	
	private Date updateDate(Date date, int field, int days) {
		
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(field, days);
	    
	    return cal.getTime();
	}
	
	private void clickAlertOk() throws Exception {
		
		driver.findElement(By.xpath("//button[contains(text(), 'Ok')]")).click();
	}
	
	private void setPaymentDateOnQuotePage(Date date) throws Exception {
	
		setQuoteDate(date, GEN_QUOTE_PAYMENT_DATE_INDEX);
	}
	
	private void setBankingDateOnQuotePage(Date date) throws Exception {
		
		setQuoteDate(date, GEN_QUOTE_PAYMENT_DATE_INDEX);
	}

	private void setDueDateOnQuotePage(Date date) throws Exception {
		
		setQuoteDate(date, GEN_QUOTE_DUE_DATE_INDEX);
	}
	
	private void setQuoteDate(Date date, int index) throws Exception {
		
		List<WebElement> dateTimePickers = getDateTimePickers();
		
		assertEquals("Unexpected number of date time picker elements on quote payment page", 3, dateTimePickers.size());

		dateTimePickers.get(index).clear();
		dateTimePickers.get(index).sendKeys(new SimpleDateFormat("MM/dd/yyyy").format(date));		
	}
	
	private int daysBetween(Date d1, Date d2)
	{
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
}
