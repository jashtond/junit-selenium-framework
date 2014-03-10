package com.lotaris.selenium.wait;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Wait for the presence / absence of an element in the DOM. Staleness
 * check by default for the absence of an element.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface WaitStaleness {
	/**
	 * @return The value that refers to a field on a IPageBlock
	 */
	public String value();

	/**
	 * @return Define if the condition for visibility or Invisibility
	 */
	public boolean not() default false;
}
