package com.lotaris.selenium.page;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow to add a description to a page object class that is used to build the
 * common information (eg: get the title to validate that a page is the right one)
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageDescriptor {
	/**
	 * @return The name of a page
	 */
	String name();
	
	/**
	 * @return URL of a page
	 */
	String url();
	
	/**
	 * @return Title of a page
	 */
	String title();
	
	/**
	 * @return Base url if it is required to override the default one
	 */
	String baseUrl() default "";
	
	/**
	 * @return If true, check of title is disabled
	 */
	boolean disableTitleCheck() default false;
	
	/**
	 * @return If true, check of URL is disabled
	 */
	boolean disableUrlCheck() default false;
}
