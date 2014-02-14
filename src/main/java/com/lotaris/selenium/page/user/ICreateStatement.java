package com.lotaris.selenium.page.user;

import com.lotaris.selenium.page.user.User;
import com.lotaris.selenium.page.user.UserGenerationException;

/**
 * Create statement allows to create new user
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public interface ICreateStatement {
	/**
	 * Prepare a before statement
	 * 
	 * @param userGenerator The user generator to use
	 * @return The user created
	 * @exception UserGenerationException When an error occurs during user generation
	 */
	User create() throws UserGenerationException;
}
