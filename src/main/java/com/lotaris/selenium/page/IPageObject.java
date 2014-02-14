package com.lotaris.selenium.page;

/**
 * Define what is a page object and force the page objects
 * to implement a method to check the validity of the page.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public interface IPageObject {
	/**
	 * Do some checks to validate the state of the abstract page is usable
	 * 
	 * @throws IllegalStateException In case the page cannot be used correctly
	 */
	void checks() throws IllegalStateException;
	
	/**
	 * Do some checks to validate the state of the abstract page is usable
	 * 
	 * @param url The url to check to be sure that the page is the right one
	 * @throws IllegalStateException In case the page cannot be used correctly
	 */
	void checks(String url) throws IllegalStateException;
}
