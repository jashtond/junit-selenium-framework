package com.lotaris.selenium.page;

import com.lotaris.selenium.IConfiguration;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Pattern;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Shared structure for PageObject and PageElement that use
 * the same kind of objects.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
abstract class PageBlock implements IPageBlock {
	private static final Pattern URL_PATH_PARAMETER_PATTERN = Pattern.compile(".*\\{[0-9]+\\}");

	// Maximum wait time (in seconds) for a page element to be found/displayed/hidden
	public static final Integer WAIT_TIME = 10;

	/**
	 * Web driver
	 */
	WebDriver driver;
	
	/**
	 * Constructor.
	 *
	 * @param webDriver The driver to use to navigate the pages
	 * @throws PageInitializationException If the page cannot be instantiated (no right title, no right url, ...)
	 */
	public PageBlock(WebDriver webDriver) {
		build(webDriver);
	}
	
	/**
	 * Internal constructor
	 * 
	 * @param webDriver The web driver
	 */
	private void build(WebDriver webDriver) {
		driver = webDriver;
		protectedBuild(webDriver);
	}
	
	/**
	 * Internal builder of the object
	 * 
	 * @param webDriver Web driver
	 */
	abstract void protectedBuild(WebDriver webDriver);
	
	/**
	 * Initialize the page object and enforce that the page is valid
	 * 
	 * @param <T> The page type to initPage
	 * @param pageClass The page class of the page to initPage
	 * @return The page initialized and valid to continue testing
	 */
	protected <T extends IPageObject> T initPage(Class<T> pageClass) {
		return initPage(driver, pageClass, null, null);
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
		return initPage(driver, pageClass, urlToCheck, null);
	}
	
	/**
	 * Initialize the page object and enforce that the page is valid
	 * 
	 * @param <T> The page type to initPage
	 * @param pageClass The page class of the page to initPage
	 * @param titleFormater The title formatter to check the title correctly
	 * @return The page initialized and valid to continue testing
	 */
	protected <T extends IPageObject> T initPage(Class<T> pageClass, PageTitle titleFormater) {
		return initPage(driver, pageClass, null, titleFormater);
	}

	/**
	 * Initialize the page object and enforce that the page is valid
	 * 
	 * @param <T> The page type to initPage
	 * @param pageClass The page class of the page to initPage
	 * @param urlToCheck The url to be checked
	 * @param titleFormater The title formatter to check the title correctly
	 * @return The page initialized and valid to continue testing
	 */
	protected <T extends IPageObject> T initPage(Class<T> pageClass, String urlToCheck, PageTitle titleFormater) {
		return initPage(driver, pageClass, urlToCheck, titleFormater);
	}

	/**
	 * Internal initialization of a page
	 * 
	 * @param <T> The page class type
	 * @param driver The web driver
	 * @param pageClass The page class to instantiate
	 * @param urlToCheck The URL to check
	 * @param titleFormatter The title formatter to check
	 * @return The page initialized
	 */
	private static <T extends IPageObject> T initPage(WebDriver driver, Class<T> pageClass, String urlToCheck, PageTitle titleFormatter) {
		T page = PageFactory.initElements(driver, pageClass);
		page.checks(urlToCheck, titleFormatter);
		return page;
	}
	
	/**
	 * Load a page from a string
	 * 
	 * @param url The URL to load
	 */
	protected void loadUrl(String url) {
		driver.get(url);
	}

	/*
	 * Navigate to a specific URL and retrieve the page expected. Checks are done against
	 * the page tile given by the annotation on the page class and the URl given.
	 * 
	 * @param <T> The page title type
	 * @param urlToNavigate The URL where to go
	 * @param pageClass The page class expected to be at the target URL
	 * @return The page object retrieved
	 */
	protected <T extends IPageObject> T navigateTo(String urlToNavigate, Class<T> pageClass) {
		return navigateTo(urlToNavigate, pageClass, null);
	}
	
	/**
	 * Navigate to a specific URL and retrieve the page expected. Checks are done against
	 * the page tile given and the URl given.
	 * 
	 * @param <T> The page title type
	 * @param urlToNavigate The URL where to go
	 * @param pageClass The page class expected to be at the target URL
	 * @param pageTitle The page title expected to be at the target URL
	 * @return The page object retrieved
	 */
	protected <T extends IPageObject> T navigateTo(String urlToNavigate, Class<T> pageClass, PageTitle pageTitle) {
		loadUrl(urlToNavigate);
		T page = PageFactory.initElements(driver, pageClass);
		page.checks(urlToNavigate, pageTitle);
		return page;
	}
	
	/**
	 * Build an URL based on the description
	 * 
	 * @param configuration Configuration to get the base URL
	 * @param descriptor The page descriptor which contains the info to build the URL
	 * @return The URL built
	 */
	static String buildUrl(IConfiguration configuration, PageDescriptor descriptor) {
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
					queryStringBuilder
						.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()))
						.append("=")
						.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()));

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
	public static String getUrl(IConfiguration configuration, Class<? extends IPageObject> pageClass, String ... parameters) {
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
	 * Get a title formatter with initial state of parameters given
	 * 
	 * @param name The name of parameter
	 * @param value The value of parameter
	 * @return The title formatter
	 */
	protected PageTitle title(String name, String value) {
		return new PageTitle().add(name, value);
	}
	
	/**
	 * This method is a workaround for a known bug of the current Selenium 
	 * version. As it seems Selenium cannot click on an element that is outside
	 * the screen, we scroll to the position of the button before clicking. 
	 * 
	 * @param by The element to scroll to and click
	 */
	public void scrollAndClick(WebElement we) {
	   int elementPosition = we.getLocation().getY();
	   // window.scroll should be supported by major browsers
	   String js = String.format("window.scroll(0, %s)", elementPosition);
	   ((JavascriptExecutor)driver).executeScript(js);
	   we.click();
	}		
}
