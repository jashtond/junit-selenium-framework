package com.lotaris.selenium.page;

import com.lotaris.selenium.wait.WaitWrapper;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.lotaris.selenium.page.PageBlock.WAIT_TIME;

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
	public static <T extends IPageObject> T initElements(final WebDriver webDriver, Class<T> pageClassToProxy) {
		T page = instantiatePage(webDriver, pageClassToProxy);
		org.openqa.selenium.support.PageFactory.initElements(new PageDecorator(page, webDriver, new DefaultElementLocatorFactory(webDriver)), page);
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
		Enhancer pageElementProxy = new Enhancer();

		pageElementProxy.setCallback(new PageObjectHandler(webDriver));
		pageElementProxy.setInterfaces(new Class[] {IPageObject.class});
		pageElementProxy.setSuperclass(pageClassToProxy);

		return (T) pageElementProxy.create(new Class[] { WebDriver.class }, new Object[] { webDriver });
	}
	
	/**
	 * The page object handler class provide the mechanism to organize a page object
	 * like we do the page element and then to offer the wait before/after mechanism around
	 * the method invoked on the page object.
	 */
	public static class PageObjectHandler implements MethodInterceptor {
		
		private final WebDriver webDriver;

		public PageObjectHandler(WebDriver webDriver) {
			this.webDriver = webDriver;
		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			try {
				// Create the wait wrapper based on the page object and other required stuff
				WaitWrapper waitWrapper = new WaitWrapper((IPageBlock) obj, new WebDriverWait(webDriver, WAIT_TIME), method);
				
				// Manage the before/after wait around the page object method invocation
				waitWrapper.manageBefore();
				Object result = proxy.invokeSuper(obj, args);
				waitWrapper.manageAfter();

				return result;
			}
			catch (IllegalAccessException | InstantiationException e) {
				throw new RuntimeException("Unexpected error", e);
			}
		}
	}
}
