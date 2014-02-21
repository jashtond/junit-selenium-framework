package com.lotaris.selenium.page;

import com.lotaris.selenium.IConfiguration;
import com.lotaris.selenium.test.TestUtils;
import org.openqa.selenium.WebDriver;

import static com.lotaris.selenium.page.PageBlock.getUrl;

/**
 * Contains methods common to all the page objects.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public abstract class PageObject extends PageBlock implements IPageObject {
	/**
	 * Page descriptor to build and retrieve information about a page
	 */
	private PageDescriptor descriptor;
	
	/**
	 * The configuration
	 */
	private IConfiguration configuration;
	
	
	/**
	 * Constructor
	 * 
	 * @param webDriver The web driver
	 */
	public PageObject(WebDriver webDriver) {
		super(webDriver);
	}
	
	/**
	 * Internal constructor
	 * 
	 * @param webDriver The web driver
	 */
	@Override
	void protectedBuild(WebDriver webDriver) {
		descriptor = getClass().getAnnotation(PageDescriptor.class);
		
		configuration = getConfiguration();
		
		if (configuration == null) {
			throw new IllegalStateException("Configuration cannot be null at construction time. You need to provide a proper implementation of getConfiguration()");
		}
	}
	
	/**
	 * @return Retrieve the configuration to configure the abstract page
	 */
	protected abstract IConfiguration getConfiguration();
	
	/**
	 * Get an URL for a specific page object
	 * 
	 * @param pageClass The page object to retrieve the URL
	 * @return The URL found
	 */
	public String getUrl(Class<? extends PageObject> pageClass, String ... parameters) {
		return getUrl(configuration, pageClass, parameters);
	}
	
	/**
	 * Build a custom URL with the base path from the config
	 * 
	 * @param path The path to add to the base path
	 * @return The custom URL built
	 */
	public String buildUrl(String path) {
		return buildUrl(configuration, path, null);
	}	
	
	/**
	 * Load a page from its type
	 * 
	 * @param pageClass The page class to get the URL to load
	 */
	protected void loadPage(Class<? extends PageObject> pageClass) {
		loadUrl(getUrl(pageClass));
	}
	
	@Override
	public final void checks() throws IllegalStateException {
		checks(getConfiguration(), descriptor, driver, null, null);
	}
	
	@Override
	public final void checks(String url) throws IllegalStateException {
		checks(getConfiguration(), descriptor, driver, url, null);
	}

	@Override
	public final void checks(PageTitle titleFormatter) throws IllegalStateException {
		checks(getConfiguration(), descriptor, driver, null, titleFormatter);
	}

	@Override
	public final void checks(String url, PageTitle titleFormatter) throws IllegalStateException {
		checks(getConfiguration(), descriptor, driver, url, titleFormatter);
	}
	
	/**
	 * General method to do several checks against a page that is loaded
	 * 
	 * @param configuration The configuration to build the URL if necessary
	 * @param descriptor The page descriptor to check
	 * @param driver The web driver
	 * @param url the URL to check
	 * @param titleFormatter The title formatter
	 * @throws IllegalStateException When the title or URL does not match
	 */
	private static void checks(IConfiguration configuration, PageDescriptor descriptor, WebDriver driver, String url, PageTitle titleFormatter) throws IllegalStateException {
		if (descriptor == null) {
			throw new IllegalArgumentException("The page description must be present on the class.");
		}
		
		try {
			if (!descriptor.disableTitleCheck()) {
				TestUtils.checkTitle(driver, titleFormatter == null ? descriptor.title() : titleFormatter.format(descriptor.title()), descriptor.name());
			}
			
			if (!descriptor.disableUrlCheck()) {
				TestUtils.checkPageUrl(driver, url == null ? buildUrl(configuration, descriptor) : url, descriptor.name(), descriptor.checkWithoutQueryString());
			}
		}
		catch (PageInitializationException pie) {
			throw new IllegalStateException(pie);
		} 		
	}
}
