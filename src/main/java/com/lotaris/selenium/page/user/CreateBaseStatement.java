package com.lotaris.selenium.page.user;

import com.lotaris.selenium.page.user.User;
import com.lotaris.selenium.page.user.UserGenerationException;
import com.lotaris.selenium.page.user.IUserGenerator;

/**
 * Create base statement to generate a user
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
class CreateBaseStatement extends BaseStatement implements ICreateStatement {
	/**
	 * Constructor
	 * 
	 * @param userGenerator The user generator
	 */
	public CreateBaseStatement(IUserGenerator userGenerator) {
		super(userGenerator);
	}
	
	@Override
	public User create() throws UserGenerationException {
		return userGenerator.create();
	}
}
