package com.lotaris.selenium.wait;

import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Define what is a wait condition class. This kind of class should
 * provide a way to build a Selenium condition that can be used in the 
 * wait mechanism from Selenium.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public interface IWaitCondition {
	/**
	 * @return The condition ready to use in the wait process
	 */
	ExpectedCondition<?> build();
}
