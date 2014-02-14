package com.lotaris.selenium.page.user;

/**
 * Special generator to allow the user creation to be used
 * in a login form
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public interface IUserGenerator {
	/**
	 * Generate a new user
	 * 
	 * @return The user created
	 */
	User create();
	
	/**
	 * Clean up the user created
	 * 
	 * @param user The user to create
	 */
	void delete(User user);
}
