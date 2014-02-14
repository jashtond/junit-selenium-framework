package com.lotaris.selenium.page.user;

import com.lotaris.selenium.page.user.IUserGenerator;

/**
 * Base statement to have the user generator
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
abstract class BaseStatement {
	/**
	 * user generator
	 */
	protected IUserGenerator userGenerator;

	/**
	 * Constructor
	 * 
	 * @param userGenerator The user generator
	 */
	BaseStatement(IUserGenerator userGenerator) {
		this.userGenerator = userGenerator;
	}
}
