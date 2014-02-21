package com.lotaris.selenium.page;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

/**
 * Similar to PageFactory but adapted to handle AbstractPageElement as well.
 * This factory uses the the PageElementDecorator to extends the decoration of
 * fields to object extending the class AbstractPageElement. The behavior is the
 * same as before for WebElement and List<WebElement>.
 *
 * @author Alain Lala <alain.lala@lotaris.com>
 * @author Laurent Prevost <laurent.prevost@lotaris.com
 */
public class PageFactory {

	/**
	 * Instantiate an instance of the given class, and set a lazy proxy for each
	 * of the WebElement and List<WebElement> fields that have been declared; by
	 * default it assumes that the field name is the HTML element's "id" or
	 * "name". Also instantiate objects for each of the declared fields
	 * extending AbstractPageElement.
	 *
	 * @see FindBy
	 * @see FindBys
	 * @see FindAll
	 *
	 * @param <T> Template class
	 * @param webDriver The driver that will be used to look up the elements
	 * @param pageClassToProxy A class which will be initialized.
	 * @return An instantiated instance of the class
	 */
	public static <T extends IPageObject> T initElements(WebDriver webDriver, Class<T> pageClassToProxy) {
		T page = instantiatePage(webDriver, pageClassToProxy);
		final WebDriver driverRef = webDriver;
		org.openqa.selenium.support.PageFactory.initElements(new PageDecorator(page, webDriver, new DefaultElementLocatorFactory(driverRef)), page);
		return page;
	}

	/**
	 * Initiate a page from the driver by the page class to proxy
	 * 
	 * @param <T> The type of the page instantiated
	 * @param webDriver The web driver
	 * @param pageClassToProxy The page class
	 * @return The page created
	 */
	private static <T extends IPageObject> T instantiatePage(WebDriver webDriver, Class<T> pageClassToProxy) {
		try {
			try {
				Constructor<T> cons = pageClassToProxy.getConstructor(WebDriver.class);
				return cons.newInstance(webDriver);
			} 
			catch (InvocationTargetException e) {
				return pageClassToProxy.newInstance();
			}
		} 
		catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
}
