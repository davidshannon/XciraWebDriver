package com.xcira.server.webdriver.testscases;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class FloorplanningAddLoan {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://ng.xcira.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testFloorplanningAddLoan() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("widgets/display/form/Input_0")).clear();
    driver.findElement(By.id("widgets/display/form/Input_0")).sendKeys("ro@ro.com");
    driver.findElement(By.id("widgets/display/form/Password_0")).clear();
    driver.findElement(By.id("widgets/display/form/Password_0")).sendKeys("ro");
    driver.findElement(By.cssSelector("button.signInButton")).click();
    driver.findElement(By.xpath("//li[@id='widgets/display/menu/MenuItem_0']/div")).click();
    driver.findElement(By.cssSelector("span.dgrid-pagination-links > span.dgrid-page-link")).click();
    
    //#widgets\2f display\2f data\2f DataGrid_8 > div.dgrid-scroller
    //driver.findElement(By.xpath("//[contains(concat(' ', @class, ' '), ' .dealerDetail ') and contains(., 'c3s4_ie_dealer']")).click();
    
    List<WebElement> dgridRows = driver.findElement(By.cssSelector(".dgrid-row-table")).findElements(By.cssSelector(".dgridRow"));
    
    System.out.println(dgridRows.size());
    for(WebElement row : dgridRows) {
    	
    	System.out.println(row.findElement(By.cssSelector(".dgrid-column-1")).getText());
    }
    
    
    /*
    driver.findElement(By.id("widgets/display/form/Button_306")).click();
    driver.findElement(By.id("widgets/display/form/Input_1")).clear();
    driver.findElement(By.id("widgets/display/form/Input_1")).sendKeys("12345678901234567");
    driver.findElement(By.cssSelector("button.alertButton")).click();
    driver.findElement(By.id("widgets/display/form/Input_2")).click();
    driver.findElement(By.id("widgets/display/form/Input_2")).clear();
    driver.findElement(By.id("widgets/display/form/Input_2")).sendKeys("Subaru");
    driver.findElement(By.id("widgets/display/form/Input_3")).clear();
    driver.findElement(By.id("widgets/display/form/Input_3")).sendKeys("Outback");
    driver.findElement(By.id("widgets/display/form/Input_4")).clear();
    driver.findElement(By.id("widgets/display/form/Input_4")).sendKeys("1998");
    driver.findElement(By.id("widgets/display/form/Input_5")).clear();
    driver.findElement(By.id("widgets/display/form/Input_5")).sendKeys("Green");
    driver.findElement(By.id("widgets/display/form/Input_6")).clear();
    driver.findElement(By.id("widgets/display/form/Input_6")).sendKeys("10000000");
    driver.findElement(By.id("widgets/display/form/Input_12")).clear();
    driver.findElement(By.id("widgets/display/form/Input_12")).sendKeys("250.00");
    driver.findElement(By.xpath("(//input[@value='▼ '])[2]")).click();
    driver.findElement(By.cssSelector("span.dijitA11ySideArrow")).click();
    driver.findElement(By.xpath("//table[@id='dijit_form_DateTextBox_2_popup']/tbody/tr[3]/td/span")).click();
    driver.findElement(By.xpath("(//input[@value='▼ '])[3]")).click();
    driver.findElement(By.xpath("//table[@id='dijit_form_DateTextBox_3_popup']/thead/tr/th/span[2]")).click();
    driver.findElement(By.xpath("//table[@id='dijit_form_DateTextBox_3_popup']/tbody/tr[3]/td/span")).click();
    driver.findElement(By.cssSelector("button.addBtn.bgcolor")).click();
    driver.findElement(By.cssSelector("button.alertButton")).click();
    driver.findElement(By.id("widgets/display/form/Input_6")).clear();
    driver.findElement(By.id("widgets/display/form/Input_6")).sendKeys("10000");
    driver.findElement(By.cssSelector("button.addBtn.bgcolor")).click();
    driver.findElement(By.cssSelector("button.alertButton")).click();
    driver.findElement(By.cssSelector("img.logoutIcon")).click();
    driver.findElement(By.cssSelector("button.alertButton")).click();
    */
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
