package com.lotaris.selenium.page.login;

import com.lotaris.selenium.page.IPageObject;

/**
 * Define what a login should be able to do.
 * 
 * Basically, a login page should allow to login with a 
 * username and a password. The login return the page
 * that is expected once the login succeed.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 * 
 * @param <PAGE_AFTER_LOGIN> The type of page got after a login
 */
public interface ILoginPage<PAGE_AFTER_LOGIN extends IPageObject> extends IPageObject {
	/**
	 * Process the login
	 * 
	 * @param userName The username
	 * @param password The password
	 * @return The page once the login succeed
	 */
	PAGE_AFTER_LOGIN login(String userName, String password);
}
