package com.lotaris.selenium.page.login;

import com.lotaris.selenium.page.user.IUserGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows to setup a test to be logged in/out
 * automatically.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginWith {
	/**
	 * @return Allow to avoid automatic logout
	 */
	boolean logout() default true;
	
	/**
	 * @return User generator class that will create a new user, override username/password/random behavior
	 */
	Class<? extends IUserGenerator> user();
}
