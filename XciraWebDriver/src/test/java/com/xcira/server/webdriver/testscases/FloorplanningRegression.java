package com.xcira.server.webdriver.testscases;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FloorplanningRegression extends FloorPlanningBase {
	
	private static int START_TIME = 0;
	private static int LICENCE_EXPIRATION = 2;
	private static int UCC1_EXPIRATION = 3;
	private static int INSURANCE_EXPIRATION = 4;
	private static int BOND_EXPIRATION = 5;
	private static int RECENT_SIGNED_DOCS = 6;
	private static int BUSINESS_FINAINCIALS = 7;
	private static int PERSONAL_FINANCE = 8;
	private static final int QUOTE_LINE_LOAN_DATE=3;
	private static final int QUOTE_LINE_DUE_DATE=14;
	
	private static String dealerName;
	
	private static final double loanAmount[] = {
			15555.55,
			10000.00,
			25400.35,
			26500.66,
			30000.00
	};

	public void testCreateDealer() throws Exception {

		Calendar calendar = Calendar.getInstance();
		String emailPrefix = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
		String today = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());

		calendar.add(Calendar.YEAR, 1);
		String nextYear = new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());

		dealerName = "WebDriver_" + emailPrefix;
		
		loginFloorplanning("ro@ro.com", "ro");
		getMenuItem("Dealers").click();
		
		pause(10000);
		
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

		pause(1000);
		assertEquals("You Have Successfully Created a Dealer",driver.findElement(By.cssSelector(".alertMessage")).getText());
		find("css=button.alertButton").click();
		
		pause(1000);
		
		log(true, "Test Create Dealer");
	}
	
	public void testDealerDetails() throws Exception {
		
		goToDealerDetailPage(dealerName);
		
		assertEquals("Rosalie", driver.findElement(By.cssSelector("span.userName")).getText());
		
		pageOptionButtons = getPageOptionButtons();
		
		assertEquals("invalid page option button count: ", 6, pageOptionButtons.size());
		
		clickPageOptionButton("Print");
		
		log(true, "Test Create Dealer");

	}
	
	public void testVinLookup() throws Exception {
		
		goToDealerDetailPage(dealerName);
		
		find("css=button.addVehicle").click();
		find("name=fields.vin").sendKeys("12345678901234567");
		assertEquals("Duplicate VIN", find("css=div.alertTitle").getText());
		find("css=button.alertButton").click();
		
		find("name=fields.vin").clear();
		find("name=fields.vin").sendKeys("WDDGF8BB3CF919444");
		
		WebElement vinLookupResult = find("css=div.vinLookUpResult");
		vinLookupResult.findElement(By.xpath("//td[contains(text(), 'WDDGF8BB3CF919444')]"));
		vinLookupResult.findElement(By.xpath("//td[contains(text(), 'Mercedes-Benz')]"));
		vinLookupResult.findElement(By.xpath("//td[contains(text(), 'C-Class')]"));
		vinLookupResult.findElement(By.xpath("//td[contains(text(), '2012')]"));
		
		vinLookupResult.click();
		
		assertEquals("Mercedes-Benz", driver.findElement(By.name("fields.make")).getAttribute("value"));
		assertEquals("C-Class", driver.findElement(By.name("fields.model")).getAttribute("value"));
		assertEquals("2012", driver.findElement(By.name("fields.year")).getAttribute("value"));
		
	}
	
	public void testAddVehicle() throws Exception {
	
		goToDealerDetailPage(dealerName);
		
		for(int i = 0, dateOffset = 1; i < 5; i++, dateOffset++) {
			
			addVehicle("1MELM5049PA644116", "WdMake_" + i, "WdModel_" + i, "200" + i, "WdColor_" + i, Integer.toString(i*10000), Double.toString(loanAmount[i]), -dateOffset*3);
		}
	}
	
	public void testGenerateQuotePage() throws Exception {
				
		goToGenerateQuotePage(dealerName, 0);

		WebElement saveQuoteButton = driver.findElement(By.cssSelector("button.compute"));
		WebElement quoteLine = driver.findElement(By.cssSelector("div.quoteLine"));		
		
		validateVehicalQuotePage(quoteLine, "91", "91", 0);
		
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
	
	public void runAllTests() throws Exception {
		
		openReport("FLoorplanningRegression");
		
		try {
			
			setupWebDriver(getProperty(BROWSER_NAME), getProperty(BROWSER_PATH), getProperty(URL), getProperty(GECKO_PATH), getProperty(IMPLICITE_WAIT));
			
			testCreateDealer();
			/*testDealerDetails();
			testVinLookup();
			testAddVehicle();
			testGenerateQuotePage();*/
		
		} catch (Exception e) {
			
			log(false, "Exception: " + e.getMessage());
		}

		
	}
	
	public static void main(String args[]) throws Exception {
	
		FloorplanningRegression floorplanningRegression = new FloorplanningRegression();
		
		try {
				
			floorplanningRegression.loadProperties(args[0]);

			floorplanningRegression.runAllTests();
		} catch(Exception e) {
			
			e.printStackTrace();

		} finally {
			
			floorplanningRegression.close();
		}

	}
}
