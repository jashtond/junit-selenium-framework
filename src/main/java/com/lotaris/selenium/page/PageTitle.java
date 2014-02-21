package com.lotaris.selenium.page;

import java.util.HashMap;
import java.util.Map;

/**
 * The title formatter class helps to format a title to be checked correctly
 * getting the title from the annotation and by replacing the parameters
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class PageTitle {
	/**
	 * Parameters to be replaced in the title
	 */
	private Map<String, String> parameters = new HashMap<>();
	
	/**
	 * Add a new parameter
	 * 
	 * @param name The name of param
	 * @param value The value of param
	 * @return this
	 */
	public PageTitle add(String name, String value) {
		parameters.put(name, value);
		return this;
	}
	
	/**
	 * Format the title
	 * 
	 * @param title The title to format
	 * @return The title formatted
	 */
	public String format(String title) {
		for (Map.Entry<String, String> param : parameters.entrySet()) {
			title = title.replaceAll("\\{" + param.getKey() + "\\}", param.getValue());
		}
		
		return title;
	}
}
