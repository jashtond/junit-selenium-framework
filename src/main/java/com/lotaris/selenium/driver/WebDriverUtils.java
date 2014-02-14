package com.lotaris.selenium.driver;

import com.lotaris.selenium.IConfiguration;
import com.lotaris.selenium.page.IPageObject;
import com.lotaris.selenium.page.PageDescriptor;
import com.lotaris.selenium.page.PageFactory;
import com.lotaris.selenium.page.PageObject;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to facilitate the writing of Selenium-based tests. Notice that this 
 * utility class is NOT thread-safe.
 * 
 * The webdrivers built there supports to run into XVFB environment.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class WebDriverUtils {
	private static final Logger LOG = LoggerFactory.getLogger(WebDriverUtils.class);
	private static WebDriver driver;
	
	/**
	 * Initialize the driver.
	 */
	public static void initDriver(IConfiguration configuration) {
		// Build the right driver
		driver = configuration.getDriverName().build(configuration);
		
		// Resize opened browser page
		Dimension d = new Dimension(2400, 1300);
		driver.manage().window().setSize(d);
		Point p = new Point(0, 0);
		driver.manage().window().setPosition(p);
		
		// Set default timeouts
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
	}
	
	/**
	 * Close the current browser.
	 */
	public static void closeDriver() {
		driver.quit();
	}
	
	/**
	 * Get the starting page by given the required page to get 
	 * for the entry point in the web application to test.
	 * 
	 * @param <T> The entry page type
	 * @param configuration The configuration to get the base URL
	 * @param pageClass The concrete class of the page to start from
	 * @return The page object retrieved from the page class
	 */
	public static <T extends IPageObject> T startFromBaseUrl(IConfiguration configuration, Class<T> pageClass) {
		if (driver == null) {
			throw new IllegalStateException("The web driver is null. It probably means that your test class does not instantiate it in @BeforeClass or @Before");
		}
		
		driver.manage().deleteAllCookies();
		driver.get(configuration.getBaseUrl());
		T page = PageFactory.initElements(driver, pageClass);
		page.checks();
		return page;
	}
	
	/**
	 * Get the starting page by retrieving the URL from the page descriptor
	 * annotation and the base URL got from the configuration. Init the page
	 * object from the result obtained by the request to the URL built.
	 * 
	 * @param <T> The entry page type
	 * @param pageClass The concrete class of the page to start from
	 * @return The page object retrieved from the page class
	 */
	public static <T extends IPageObject> T startFromPage(IConfiguration configuration, Class<T> pageClass) {
		driver.manage().deleteAllCookies();

		PageDescriptor pageDescriptor = pageClass.getAnnotation(PageDescriptor.class);
		
		if (pageDescriptor == null) {
			throw new IllegalArgumentException("The page object class is not annotated with @PageDescriptor.");
		}
		
		driver.get(PageObject.buildUrl(configuration, pageDescriptor.url()));
		T page = org.openqa.selenium.support.PageFactory.initElements(driver, pageClass);
		page.checks();
		return page;
	}
		
	/**
	 * Get the starting page by retrieving the URL from the page descriptor
	 * annotation and the base URL got from the configuration. 
	 * 
	 * Init the page object from the result obtained by the request to the URL built
	 * as the expected page. This is useful when a redirection is involved.
	 * 
	 * @param <T> The expected page type
	 * @param pageClass The concrete class of the page to start from
	 * @param expectedPageClass The page that we expect to be redirected
	 * @return The page object retrieved from the expected page class
	 */
	public static <T extends IPageObject> T startFromPage(IConfiguration configuration, Class<? extends PageObject> pageClass, Class<T> expectedPageClass) {
		driver.manage().deleteAllCookies();
		
		PageDescriptor pageDescriptor = pageClass.getAnnotation(PageDescriptor.class);
		
		if (pageDescriptor == null) {
			throw new IllegalArgumentException("The page object class is not annotated with @PageDescriptor.");
		}
		
		driver.get(PageObject.buildUrl(configuration, pageDescriptor.url()));
		T page = org.openqa.selenium.support.PageFactory.initElements(driver, expectedPageClass);
		page.checks();
		return page;
	}		
	
	/**
	 * Takes a screenshot of the current browser page and saves it with the given name
	 * 
	 * @param configuration The configuration to help taking screenshots
	 * @param name the name of the screenshot 
	 */
	public static void takeScreenshot(IConfiguration configuration, String name) {
		File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scr, new File(configuration.getScreenShotsPath() + name + ".png"));
		} 
		catch (IOException ex) {
			LOG.warn("Unable to take a screenshot " + name + ": " + ex.getMessage());
		}
	}
}
