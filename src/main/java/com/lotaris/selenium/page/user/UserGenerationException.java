package com.lotaris.selenium.page.user;

/**
 * Exception in case of error in the login infrastructure code
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class UserGenerationException extends Exception {
	public UserGenerationException() {}

	public UserGenerationException(String message) {
		super(message);
	}

	public UserGenerationException(Throwable cause) {
		super(cause);
	}

	public UserGenerationException(String message, Throwable cause) {
		super(message, cause);
	}
}
