package com.lotaris.selenium.page.user;

import com.lotaris.selenium.page.user.User;
import com.lotaris.selenium.page.user.UserGenerationException;
import com.lotaris.selenium.page.user.IUserGenerator;

/**
 * Delete base statement to delete a user
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
class DeleteBaseStatement extends BaseStatement implements IDeleteStatement {
	/**
	 * Constructor
	 * 
	 * @param userGenerator The constructor
	 */
	public DeleteBaseStatement(IUserGenerator userGenerator) {
		super(userGenerator);
	}
	
	@Override
	public void delete(User user) throws UserGenerationException {
		userGenerator.delete(user);
	}
}
