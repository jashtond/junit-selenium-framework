package com.lotaris.selenium;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import com.lotaris.selenium.wait.IWaitCondition;
import com.lotaris.selenium.wait.Wait;
import com.lotaris.selenium.wait.WaitStaleness;
import com.lotaris.selenium.wait.WaitVisibility;
import com.lotaris.selenium.wait.WaitWrapper;
        
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

public class login {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://apps.ensoftek.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUntitled() throws Exception {
    driver.get(baseUrl + "/DrCloudEMR2013/interface/login/login_frame.php?error=1&site=");
    driver.findElement(By.name("authUser")).clear();
    driver.findElement(By.name("authUser")).sendKeys("admin");
    driver.findElement(By.name("clearPass")).clear();
    driver.findElement(By.name("clearPass")).sendKeys("demo");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.switchTo().frame("Title");
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=Title | ]]
    driver.findElement(By.xpath(".//*[@id='tb_find_pat']/form[1]/div[2]/table/tbody/tr[2]/td[2]/a/span")).click();
    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=RTop | ]]
    new Select(driver.findElement(By.id("form_title"))).selectByValue("Mr.");
    driver.findElement(By.id("form_fname")).clear();
    driver.findElement(By.id("form_fname")).sendKeys("vinneth");
    driver.findElement(By.id("form_lname")).clear();
    driver.findElement(By.id("form_lname")).sendKeys("test1");
    driver.findElement(By.id("form_mname")).click();
    driver.findElement(By.id("form_mname")).clear();
    driver.findElement(By.id("form_mname")).sendKeys("Nayak12");
    driver.findElement(By.id("form_pubpid")).click();
    driver.findElement(By.id("form_pubpid")).clear();
    driver.findElement(By.id("form_pubpid")).sendKeys("209");
    driver.findElement(By.id("img_DOB")).click();
    driver.findElement(By.xpath(".//*[@id='form_DOB']")).clear();
    driver.findElement(By.xpath(".//*[@id='form_DOB']")).sendKeys("2005-09-09");
    driver.findElement(By.id("form_sex")).click();
    new Select(driver.findElement(By.id("form_sex"))).selectByVisibleText("Male");
    driver.findElement(By.cssSelector("option[value=\"Male\"]")).click();
    driver.findElement(By.id("form_ss")).click();
    driver.findElement(By.id("form_ss")).clear();
    driver.findElement(By.id("form_ss")).sendKeys("546-54-6555");
    Thread.sleep(3000);
    driver.findElement(By.xpath(".//*[@id='create']")).click();
    Thread.sleep(5000);
    Alert alert=driver.switchTo().alert();
    alert.accept();
    
  
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
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
