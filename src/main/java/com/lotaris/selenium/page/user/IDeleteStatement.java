package com.lotaris.selenium.page.user;

import com.lotaris.selenium.page.user.User;
import com.lotaris.selenium.page.user.UserGenerationException;

/**
 * Delete statement allows to delete a user
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public interface IDeleteStatement {
	/**
	 * Prepare the the after statement
	 * 
	 * @param userGenerator The user generator to use
	 * @param user The user to user
	 * @exception UserGenerationException When an error occurs during user generation
	 */
	void delete(User user) throws UserGenerationException ;
}
