package com.lotaris.selenium.page;

/**
 * Defines a specific exception type, thrown by the constructor of the PO.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class PageInitializationException extends Exception {
	/**
	 * Constructor
	 * 
	 * @param message Message
	 */
	public PageInitializationException(String message) {
		super(message);
	}
}
