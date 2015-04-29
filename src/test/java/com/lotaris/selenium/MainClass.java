package com.lotaris.selenium;

import com.lotaris.selenium.IConfiguration;
import com.lotaris.selenium.driver.WebDriverUtils;
import com.lotaris.selenium.driver.DriverFactory;
import com.lotaris.selenium.expectation.ExtendedExpectedConditions;
import com.lotaris.selenium.utils.ReflectionUtils;

public class MainClass extends c{
    
}
public void main setup() throws Exception { 
    setUp("http://www.google.com/", "*firefox");
}
    public void testNew() throws Exception {
        selenium.open("/");
        selenium.type("q", "selenium rc");
        selenium.click("btnG");
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("results *for selenium rc"));
}
