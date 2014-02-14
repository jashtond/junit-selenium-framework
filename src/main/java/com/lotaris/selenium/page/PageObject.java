package com.lotaris.selenium.page;

import com.lotaris.selenium.IConfiguration;
import com.lotaris.selenium.test.TestUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains methods common to all the page objects.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public abstract class PageObject implements IPageObject {
	private static final Logger LOG = LoggerFactory.getLogger(PageObject.class);

	private static final Pattern URL_PATH_PARAMETER_PATTERN = Pattern.compile(".*\\{[0-9]+\\}");

	private static final String UTF8 = "UTF-8";
	
	// Maximum wait time (in seconds) for a page element to be found/displayed/hidden
	public static final Integer WAIT_TIME = 10;

	/**
	 * Web driver
	 */
	private WebDriver driver;

	// Fluent wait which handles waiting for page elements
	protected WebDriverWait wait;

	/**
	 * Page descriptor to build and retrieve information about a page
	 */
	private PageDescriptor descriptor;
	
	/**
	 * The configuration
	 */
	private IConfiguration configuration;
	
	/**
	 * Constructor.
	 *
	 * @param webDriver The driver to use to navigate the pages
	 * @throws PageInitializationException If the page cannot be instantiated (no right title, no right url, ...)
	 */
	public PageObject(WebDriver webDriver) {
		build(webDriver);
	}
	
	/**
	 * Internal constructor
	 * 
	 * @param webDriver The web driver
	 */
	private void build(WebDriver webDriver) {
		driver = webDriver;
		wait = new WebDriverWait(driver, WAIT_TIME);

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
	
	@Override
	public void checks() throws IllegalStateException {
		checks(buildUrl(configuration, descriptor));
	}
	
	@Override
	public void checks(String url) throws IllegalStateException {
		if (descriptor == null) {
			throw new IllegalArgumentException("The page description must be present on the class.");
		}
		
		try {
			if (!descriptor.disableTitleCheck()) {
				TestUtils.checkTitle(driver, descriptor.title(), descriptor.name());
			}
			
			if (!descriptor.disableUrlCheck()) {
				TestUtils.checkPageUrl(driver, url, descriptor.name(), descriptor.checkWithoutQueryString());
			}
		}
		catch (PageInitializationException pie) {
			throw new IllegalStateException(pie);
		} 
	}

	/**
	 * Initialize the page object and enforce that the page is valid
	 * 
	 * @param <T> The page type to initPage
	 * @param pageClass The page class of the page to initPage
	 * @return The page initialized and valid to continue testing
	 */
	protected <T extends IPageObject> T initPage(Class<T> pageClass) {
		T page = PageFactory.initElements(driver, pageClass);
		page.checks();
		return page;
	}
	
	/**
	 * Initialize the page object and enforce that the page is valid
	 * 
	 * @param <T> The page type to initPage
	 * @param pageClass The page class of the page to initPage
	 * @param urlToCheck The url to be checked
	 * @return The page initialized and valid to continue testing
	 */
	protected <T extends IPageObject> T initPage(Class<T> pageClass, String urlToCheck) {
		T page = PageFactory.initElements(driver, pageClass);
		page.checks(urlToCheck);
		return page;
	}

	/**
	 * Load a page from its type
	 * 
	 * @param pageClass The page class to get the URL to load
	 */
	protected void loadPage(Class<? extends PageObject> pageClass) {
		loadUrl(getUrl(pageClass));
	}
	
	/**
	 * Load a page from a string
	 * 
	 * @param url The URL to load
	 */
	protected void loadUrl(String url) {
		driver.get(url);
	}
	
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
	 * Build an URL based on the description
	 * 
	 * @param configuration Configuration to get the base URL
	 * @param descriptor The page descriptor which contains the info to build the URL
	 * @return The URL built
	 */
	private static String buildUrl(IConfiguration configuration, PageDescriptor descriptor) {
		if (descriptor.baseUrl().isEmpty()) {
			return configuration.getBaseUrl() + descriptor.url();
		}
		else {
			return descriptor.baseUrl() + descriptor.url();
		}
	}
	
	/**
	 * Build a custom URL with the base path from the config
	 * 
	 * @param configuration The configuration
	 * @param path The path to add to the base path
	 * @return The custom URL built
	 */
	public static String buildUrl(IConfiguration configuration, String path) {
		return buildUrl(configuration, path, null);
	}
	
	/**
	 * Build a custom URL with the base path from the config
	 * 
	 * @param configuration Configuration to get the base URL
	 * @param path The path to add to the base path
	 * @param queryString Add query strings
	 * @return The custom URL built
	 */
	public static String buildUrl(IConfiguration configuration, String path, Map<String, String> queryString) {
		StringBuilder url = new StringBuilder();
		
		String baseUrl = configuration.getBaseUrl();
		
		// Avoid double slashes
		if (baseUrl.endsWith("/") && path.startsWith("/")) {
			url.append(baseUrl).append(path.substring(1));
		}
		
		// Avoid adding additional slash
		else if ((baseUrl.endsWith("/") && !path.startsWith("/")) || (!baseUrl.endsWith("/") && path.startsWith("/"))) {
			url.append(baseUrl).append(path);
		}
		
		// Add slash
		else {
			url.append(baseUrl).append("/").append(path);
		}
		
		// Check if there are query string
		if (queryString != null && !queryString.isEmpty()) {
			StringBuilder queryStringBuilder = new StringBuilder();
			
			// Populate the query string
			for (Map.Entry<String, String> entry : queryString.entrySet()) {
				try {
					queryStringBuilder.append(URLEncoder.encode(entry.getKey(), UTF8)).append("=").append(URLEncoder.encode(entry.getValue(), UTF8));

					if (queryStringBuilder.length() > 0) {
						queryStringBuilder.append('&');
					}
				}
				catch (UnsupportedEncodingException uee) {
					throw new RuntimeException("Unable to encode the query parameter [" + entry.getKey() + "] and value [" + entry.getValue() + "]", uee);
				}
			}
			
			String urlTest = url.toString();
			
			// Check if the url already contains the separator between path and query string
			if (urlTest.endsWith("?")) {
				url.append(queryStringBuilder.toString());
			}
			
			// Check if the url contains query strings and ends with the query string separator
			else if (urlTest.contains("?") && urlTest.endsWith("&")) {
				url.append(queryStringBuilder.toString());
			}
			
			// Append the query string
			else {
				url.append("&").append(queryStringBuilder.toString());
			}
		}
		
		return url.toString();
	}

	/**
	 * Get an URL for a specific page object
	 * 
	 * @param configuration The configuration
	 * @param pageClass The page object to retrieve the URL
	 * @return The URL found
	 */
	public static String getUrl(IConfiguration configuration, Class<? extends PageObject> pageClass, String ... parameters) {
		PageDescriptor classPageDescriptor = pageClass.getAnnotation(PageDescriptor.class);
		
		if (classPageDescriptor != null) {
			String url = buildUrl(configuration, classPageDescriptor);
			
			if (URL_PATH_PARAMETER_PATTERN.matcher(url).matches()) {
				for (int i = 0; i < parameters.length; i++) {
					url = url.replaceAll("\\{" + i + "\\}", parameters[i]);
				}
			}
			
			return url;
		}
		else {
			throw new IllegalArgumentException("Unable to find the PageDescriptor annotation on the PageClass");
		}
	}
	
	/**
	 * Waits maximum 10 seconds for an element to be invisible.
	 * 
	 * @param locator The element that is expected to be invisible
	 */
	protected void waitForInvisibility(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
}
