package com.lotaris.selenium.wait;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Wait for a specific condition given by the condition class
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Wait {
	/**
	 * @return The condition class
	 */
	public Class<? extends IWaitCondition> value();
}
