package com.lotaris.selenium;

import com.lotaris.selenium.driver.DriverFactory;

/**
 * Configuration object to help the framework to work like a charm
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public interface IConfiguration {
	/**
	 * @return The cockpit base URL
	 */
	String getBaseUrl();

	/**
	 * @return Define if the XVFB is enabled or not
	 */
	boolean isXvfbEnabled();

	/**
	 * @return Get the XVFB display port
	 */
	String getXvfbDisplay();

	/**
	 * @return Get the screen shots path for testing purpose (like upload)
	 */
	String getScreenShotsPath();
	
	/**
	 * @return Define if a proxy should be used or not
	 */
	boolean isProxyEnabled();
	
	/**
	 * @return The proxy host
	 */
	String getProxyHost();
	
	/**
	 * @return The proxy port
	 */
	String getProxyPort();
		
	/**
	 * @return The driver name
	 */
	DriverFactory.DriverName getDriverName();
	
	/**
	 * @return The Browser Firefox path. Null value if the path does not exist nor set
	 */
	String getBrowserFirefoxPath();
	
	/**
	 * @return The Browser Google Chrome path. Null value if the path does not exist nor set
	 */
	String getBrowserGoogleChromePath();
	
	/**
	 * @return The Driver Google Chrome path. Empty string if it is not possible to find a proper value for the driver path
	 */
	String getDriverGoogleChromePath();
}
