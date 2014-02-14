package com.lotaris.selenium.page.login;

import com.lotaris.selenium.page.IPageObject;

/**
 * Define what a login should be able to do.
 * 
 * The logout return the page after the logout is
 * called.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 * 
 * @param <PAGE_AFTER_LOGOUT> The type of page got after a logout
 */
public interface ILogoutPage<PAGE_AFTER_LOGOUT extends IPageObject> extends IPageObject {
	/**
	 * Process the logout
	 * 
	 * @return The page once the logout succeed
	 */
	PAGE_AFTER_LOGOUT logout();
}
