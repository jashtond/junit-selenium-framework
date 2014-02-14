package com.lotaris.selenium.test;

import com.lotaris.selenium.IConfiguration;
import com.lotaris.selenium.driver.WebDriverUtils;
import com.lotaris.selenium.page.PageObject;
import com.lotaris.selenium.page.IPageObject;

/**
 * Contains functionality common to all the tests.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public abstract class SeleniumTest {
	/**
	 * @return Retrieve a configuration
	 */
	protected abstract IConfiguration getConfiguration();
	
	/**
	 * Proxy method to the webdriver utils to start from a page
	 * 
	 * @param <T> The page type
	 * @param pageClass The page class to initialize
	 * @return The page created
	 */
	protected <T extends IPageObject> T startFromPage(Class<T> pageClass) {
		return WebDriverUtils.startFromBaseUrl(getConfiguration(), pageClass);
	}
	
	/**
	 * Proxy method to the webdriver utils to start from a page and get
	 * the expected page (eg: after a redirect).
	 * 
	 * @param <T> The expected page type
	 * @param pageClass The page class to start from
	 * @param expectedPageClass The expected page obtained after the request
	 * @return The expected page created
	 */
	protected <T extends IPageObject> T startFromPage(Class<? extends PageObject> pageClass, Class<T> expectedPageClass) {
		return WebDriverUtils.startFromPage(getConfiguration(), pageClass, expectedPageClass);
	}
	
	/**
	 * Retrieve the page URL from an abstract page class
	 * 
	 * @param pageClass The page class to retrieve the URL
	 * @return The URL found
	 */
	protected String getPageUrl(Class<? extends PageObject> pageClass) {
		return PageObject.getUrl(getConfiguration(), pageClass);
	}
}
