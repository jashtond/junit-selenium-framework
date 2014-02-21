package com.lotaris.selenium.page;

/**
 * Defines a specific exception type, thrown by the construction of Page Elements.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class PageElementInitializationException extends RuntimeException {
	public PageElementInitializationException(String message) {
		super(message);
	}
	
	public PageElementInitializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
