package com.lotaris.selenium.test;

import com.lotaris.selenium.page.PageInitializationException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Group few methods to help the test writing.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class TestUtils {
	private static final Logger LOG = LoggerFactory.getLogger(TestUtils.class);

	/**
	 * Methods that verifies the title of the currently loaded page
	 *
	 * @param driver the object holding the current page
	 * @param expectedTitle the title of the page expected to be loaded
	 * @param pageName the name of the page expected to be loaded, for incorporating in the exception message
	 * @throws PageInitializationException
	 */
	public static void checkTitle(WebDriver driver, String expectedTitle, String pageName) throws PageInitializationException {
		checkCondition(driver, expectedTitle, pageName, ExpectedConditions.titleIs(expectedTitle));
	}

	/**
	 * Methods that verifies the title of the currently loaded page (that it contains the expected title)
	 *
	 * @param driver the object holding the current page
	 * @param expectedTitle the title of the page expected to be loaded
	 * @param pageName the name of the page expected to be loaded, for incorporating in the exception message
	 * @throws PageInitializationException
	 */
	public static void checkTitleContains(WebDriver driver, String expectedTitle, String pageName) throws PageInitializationException {
		checkCondition(driver, expectedTitle, pageName, ExpectedConditions.titleContains(expectedTitle));
	}
	
	/**
	 * Methods that verifies the URL of the currently loaded page
	 *
	 * @param driver the driver object of the currently initialized browser
	 * @param expectedUrl the expected URL of the currently laoded page
	 * @param pageName the name of the page expected to be loaded, for incorporating in the exception message
	 * @throws PageInitializationException
	 */
	public static void checkPageUrl(WebDriver driver, String expectedUrl, String pageName) throws PageInitializationException {
		if (!driver.getCurrentUrl().equals(expectedUrl)) {
			String message = String.format("[%s] page URL is not correct. Expected [%s] but was [%s]", pageName, expectedUrl, driver.getCurrentUrl());
			LOG.error(message);
			throw new PageInitializationException(message);
		}
	}
	
	/**
	 * Check a page for a condition given
	 *
	 * @param driver the object holding the current page
	 * @param expectedTitle the title of the page expected to be loaded
	 * @param pageName the name of the page expected to be loaded, for incorporating in the exception message
	 * @param condition The condition to apply to the page
	 * @throws PageInitializationException
	 */
	private static void checkCondition(WebDriver driver, String expectedTitle, String pageName, 
		ExpectedCondition<Boolean> condition) throws PageInitializationException {
		
		final String message = String.format("[%s] page not initialized correctly. Expected [%s] but was [%s]", pageName, expectedTitle, driver.getTitle());
		
		try {
			Boolean result = new WebDriverWait(driver, 10).until(condition);
		
			if (result == null || !result) {
				LOG.error(message);
				throw new PageInitializationException(message);
			}
			
		} catch (TimeoutException ex) {
			LOG.error(message);
			throw new PageInitializationException(message);
		}
	}
}
