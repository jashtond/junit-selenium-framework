package com.lotaris.selenium.page.user;

/**
 * Decorator to improve the feature of a user generator
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public interface IUserGeneratorDecorator {
	/**
	 * @param userGenerator The user generator to decorate
	 */
	void decorate(IUserGenerator userGenerator);
	
	/**
	 * Create a create statement
	 * 
	 * @param createStatement The base create statement
	 * @return The new create statement
	 */
	ICreateStatement createStatement(ICreateStatement createStatement);
	
	/**
	 * Create a delete statement
	 * 
	 * @param deleteStatement The base delete statement
	 * @return The new delete statement
	 */
	IDeleteStatement deleteStatement(IDeleteStatement deleteStatement);
}
