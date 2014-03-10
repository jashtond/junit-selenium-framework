package com.lotaris.selenium.wait;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Wait for the visibility/invisibility of an element.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface WaitVisibility {
	/**
	 * @return The value that refers to a field on a IPageBlock
	 */
	public String value();

	/**
	 * @return Define if the condition for visibility or Invisibility
	 */
	public boolean not() default false;
}
