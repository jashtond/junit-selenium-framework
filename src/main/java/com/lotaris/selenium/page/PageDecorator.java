package com.lotaris.selenium.page;

import com.lotaris.selenium.utils.ReflectionUtils;
import com.lotaris.selenium.wait.WaitWrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.lotaris.selenium.page.PageBlock.WAIT_TIME;

/**
 * Class handling the "decoration" of the page object and the contained elements
 * extending AbstractPageElement.
 *
 * @author Alain Lala <alain.lala@lotaris.com>
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class PageDecorator extends DefaultFieldDecorator {
//	private static final Logger LOG = LoggerFactory.getLogger(PageDecorator.class);
	
	/**
	 * Webdriver reference to inject to Page Elements
	 */
	private WebDriver webDriver;
	
	/**
	 * The parent page to set to the page elements
	 */
	private IPageObject parentPage;
	
	/**
	 * Constructor
	 * 
	 * @param pageObject The page object
	 * @param webDriver The web driver
	 * @param factory The element locator factory
	 */
	public PageDecorator(IPageObject pageObject, WebDriver webDriver, ElementLocatorFactory factory) {
		super(factory);
		this.webDriver = webDriver;
		this.parentPage = pageObject;
	}

	@Override
	public Object decorate(ClassLoader cl, Field field) {
		// The field is not a web element (or subclass) and not a list that contains (or subclass)
		if (!(WebElement.class.isAssignableFrom(field.getType()) || isDecoratableList(field))) {
			return null;
		}

		ElementLocator locator = factory.createLocator(field);
		if (locator == null) {
			return null;
		}
		
		// Field is a single abstract page element
		if (PageElement.class.isAssignableFrom(field.getType())) {
			return proxyForAbstractPageElement(parentPage, webDriver, cl, getParameterizedType(field), locator);
		} 
		
		// Field is a list of single abstract page elements
		else if (List.class.isAssignableFrom(field.getType()) && isPageElementList(field)) {
			return proxyForListOfAbstractPageElement(parentPage, webDriver, cl, getParameterizedType(field), locator);
		} 
		
		// Field is a web element
		else if (WebElement.class.isAssignableFrom(field.getType())) {
			return proxyForLocator(cl, locator);
		} 
		
		// Field is a list of web elements
		else if (List.class.isAssignableFrom(field.getType())) {
			return proxyForListLocator(cl, locator);
		}

		return null;
	}

//	private void logFindBy(Field field) {
//		FindBy fb = field.getAnnotation(FindBy.class);
//		FindAll fa = field.getAnnotation(FindAll.class);
//		FindBys fbs = field.getAnnotation(FindBys.class);
//		
//		if (fb != null) {
//			LOG.debug("######## FindBy detected with how={}, className={}, css={}, using={}", fb.how(), fb.className(), fb.css(), fb.using());
//		}
//
//		if (fa != null) {
//			for (int i = 0; i < fa.value().length; i++) {
//				FindBy fafb = fa.value()[i];
//				LOG.debug("######## FindAll({}) detected with how={}, className={}, css={}, using={}", i, fafb.how(), fafb.className(), fafb.css(), fafb.using());
//			}
//		}
//		
//		if (fbs != null) {
//			for (int i = 0; i < fbs.value().length; i++) {
//				FindBy fbsfb = fbs.value()[i];
//				LOG.debug("######## FindBys({}) detected with how={}, className={}, css={}, using={}", i, fbsfb.how(), fbsfb.className(), fbsfb.css(), fbsfb.using());
//			}
//		}
//	}
	
	/**
	 * Check if the field is a list that can be handled by
	 * {@link PageElementDecorator}.
	 *
	 * @param field the field
	 * @return true if the field is a list, has elements that implements
	 * {@link WebElement} interface and has an annotation
	 * {@link FindBy}, {@link FindBys} or {@link FindAll}
	 */
	private boolean isDecoratableList(Field field) {
		// Check if we have a list
		if (!List.class.isAssignableFrom(field.getType())) {
			return false;
		}

		// Type erasure in Java isn't complete. Attempt to discover the generic type of the list.
		Type genericType = field.getGenericType();
		if (!(genericType instanceof ParameterizedType)) {
			return false;
		}

		// Check if the type of list is typed or not
		Class parameterizedClassForList;
		ParameterizedType listType = (ParameterizedType) genericType;
		if (listType.getActualTypeArguments()[0] instanceof ParameterizedType) {
			parameterizedClassForList = (Class) ((ParameterizedType) listType.getActualTypeArguments()[0]).getRawType();
		}
		else {
			parameterizedClassForList = (Class) listType.getActualTypeArguments()[0];
		}

		// Check the paremterized type if it is webelement or page element
		if (!WebElement.class.isAssignableFrom(parameterizedClassForList)) {
			return false;
		}

		// Check if the field has annotations
		if (field.getAnnotation(FindBy.class) == null && field.getAnnotation(FindBys.class) == null && field.getAnnotation(FindAll.class) == null) {
			return false;
		}

		return true;
	}

	/**
	 * Create a proxy of a concrete class of an AbstractPageElement to deal with the selenium webelement
	 * 
	 * @param parentPageObject The parent page object
	 * @param webDriver The web driver reference
	 * @param loader class loader The class loader
	 * @param pageElementClass The page element class to proxy
	 * @param locator the locator The locator to find the element
	 * @return The page element proxy
	 */
	private static PageElement proxyForAbstractPageElement(IPageObject parentPageObject, WebDriver webDriver, ClassLoader loader, Class<? extends PageElement> pageElementClass, ElementLocator locator) {
		return proxyForAbstractPageElement(new AbstractPageElementCallback(parentPageObject, webDriver, loader, locator, pageElementClass), pageElementClass, webDriver);
	}

	/**
	 * Create a proxy of a concrete class of an AbstractPageElement to deal with the selenium webelement
	 * 
	 * @param parentPageObject The parent page object
	 * @param webDriver The web driver reference
	 * @param loader class loader The class loader
	 * @param pageElementClass The page element class to proxy
	 * @param locator the locator The locator to find the element
	 * @return The page element proxy
	 */
	private static PageElement proxyForAbstractPageElement(IPageObject parentPageObject, WebDriver webDriver, ClassLoader loader, Class<? extends PageElement> pageElementClass, WebElement webElement) {
		return proxyForAbstractPageElement(new AbstractPageElementCallback(parentPageObject, webDriver, loader, webElement, pageElementClass), pageElementClass, webDriver);
	}
	
	/**
	 * Create a proxy of a concrete class of an AbstractPageElement to deal with the selenium webelement
	 * 
	 * @param callback The callback to use for the proxy
	 * @param pageElementClass The page element class for which the proxy is created
	 * @param webDriver The web driver involved in the operations
	 * @return The page element created which is a proxy
	 */
	private static PageElement proxyForAbstractPageElement(AbstractPageElementCallback callback, Class<? extends PageElement> pageElementClass, WebDriver webDriver) {
		try {
			pageElementClass.getConstructor(WebDriver.class);
		}
		catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Check that the " + pageElementClass.getCanonicalName() + " has a constructor(WebDriver webDriver) { super(webDriver); } implemented.", e.getCause());
		}
		
		Enhancer pageElementProxy = new Enhancer();
		
		pageElementProxy.setCallback(callback);
		pageElementProxy.setInterfaces(new Class[] {WebElement.class, WrapsElement.class, Locatable.class});
		pageElementProxy.setSuperclass(pageElementClass);
		
		return (PageElement) pageElementProxy.create(new Class[] { WebDriver.class }, new Object[] { webDriver });
	}

	/**
	 * Create a proxy of list of class of an AbstractPageElement to deal
	 * with the selenium webelement
	 * 
	 * @param parentPageObject The parent page object
	 * @param webDriver The web driver reference
	 * @param loader class loader The class loader
	 * @param pageElementClass The page element class to proxy
	 * @param locator the locator The locator to find the element
	 * @return The page element proxy list
	 */
	private static List<WebElement> proxyForListOfAbstractPageElement(IPageObject parentPageObject, WebDriver webDriver, ClassLoader loader, Class<? extends PageElement> pageElementClass, ElementLocator locator) {
		return (List<WebElement>) Proxy.newProxyInstance(
			loader, 
			new Class[] { List.class }, 
			new LocatingElementListOfAbstractPageElementHandler(parentPageObject, webDriver, locator, pageElementClass)
		);
	}

	/**
	 * Check that the field received is a list of {@link AbstractPageElement}.
	 *
	 * @param field the field to check
	 * @return true if the field is a list of {@link AbstractPageElement}
	 */
	private boolean isPageElementList(Field field) {
		if (!(field.getGenericType() instanceof ParameterizedType)) {
			return false;
		}

		return PageElement.class.isAssignableFrom(getParameterizedType(field));
	}

	/**
	 * Retrieve the type the type of a field
	 * @param field The field to get the type
	 * @return The class type of the field or the type of the list
	 */
	private Class<? extends PageElement> getParameterizedType(Field field) {
		if (List.class.isAssignableFrom(field.getType())) {
			ParameterizedType listType = (ParameterizedType) field.getGenericType();
			
			if (listType.getActualTypeArguments()[0] instanceof ParameterizedType) {
				return (Class) ((ParameterizedType) listType.getActualTypeArguments()[0]).getRawType();
			}
			else {
				return (Class) listType.getActualTypeArguments()[0];
			}
		}
		else {
			return (Class<? extends PageElement>) field.getType();
		}
	}

	/**
	 * Proxy class of a single abstract page element
	 */
	public static class AbstractPageElementCallback implements MethodInterceptor {
		/**
		 * Webdriver reference to inject to Abstract Page Element
		 */
		private final WebDriver webDriver;
		
		/**
		 * Locator to find a web element
		 */
		private ElementLocator locator;
		
		/**
		 * Webelement
		 */
		private WebElement webElement;
		
		/**
		 * The page class to instantiate
		 */
		private final Class<? extends PageElement> pageElementClass;
		
		/**
		 * The class loader to create a proxy of web element
		 */
		private final ClassLoader loader;

		/**
		 * The parent page object
		 */
		private final IPageObject parentPage;
		
		/**
		 * Constructor
		 * 
		 * @param pageObject The parent page object
		 * @param webDriver The web driver
		 * @param loader The class loader
		 * @param locator The locator for the current level
		 * @param pageElementClass The page element class
		 */
		public AbstractPageElementCallback(IPageObject parentPage, WebDriver webDriver, ClassLoader loader, ElementLocator locator, Class<? extends PageElement> pageElementClass) {
			this.webDriver = webDriver;
			this.pageElementClass = pageElementClass;
			this.locator = locator;
			this.loader = loader;
			this.parentPage = parentPage;
		}
		
		/**
		 * Constructor
		 * 
		 * @param pageObject The parent page object
		 * @param webDriver The web driver
		 * @param loader The class loader
		 * @param webElement The web element
		 * @param pageElementClass The page element class
		 */
		public AbstractPageElementCallback(IPageObject parentPage, WebDriver webDriver, ClassLoader loader, WebElement webElement, Class<? extends PageElement> pageElementClass) {
			this.webDriver = webDriver;
			this.pageElementClass = pageElementClass;
			this.webElement = webElement;
			this.loader = loader;
			this.parentPage = parentPage;
		}	
		
		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			try {
				// Retrieve the constructor of a page element that must take a webelement as parameter
				Constructor pageElementConstructor = pageElementClass.getConstructor(WebDriver.class, WebElement.class);

				// Create an invocation handler for the proxy of the webelement and create the webelement proxy with the invocation handler just created
				WebElement weProxy;
				// Check if a locator is present, if not, return the web element as a proxy
				if (locator != null) {
					weProxy = (WebElement) Proxy.newProxyInstance(loader, new Class[] {WebElement.class, WrapsElement.class, Locatable.class}, new LocatingElementHandler(locator));
				}
				else {
					weProxy = webElement;
				}

				// Create the new instance of the page element that contains a proxy to the web element
				PageElement pageElement = (PageElement) pageElementConstructor.newInstance(webDriver, weProxy);

				if (method.getName().equals("getPageObject")) {
					return parentPage;
				}

				// Inject the parent page in the page element to always have a reference to a page object from a page element as it is 
				// a composite structure without having to do it manually by the test developers.
				ReflectionUtils.injectPageObject(pageElement, parentPage);
				
				// Create a wait wrapper to manage the wait annotation if present
				WaitWrapper waitWrapper = new WaitWrapper(pageElement, new WebDriverWait(webDriver, WAIT_TIME), method);
				
				// Check if the method is a WebElement interface method, if true, apply a specific behavior
				for (Method m : WebElement.class.getMethods()) {
					// If the method comes from a web element interface
					if (m.getName().equals(method.getName())) {
						waitWrapper.manageBefore();
						Object result = method.invoke(pageElement, args);
						waitWrapper.manageAfter();
						return result;
					}
				}

				// Initialize the page element with a locator find element when locator is available, otherwise, use the web element directly
				if (locator != null) {
					PageFactory.initElements(new PageDecorator(parentPage, webDriver, new DefaultElementLocatorFactory(locator.findElement())), pageElement);
				}
				else {
					PageFactory.initElements(new PageDecorator(parentPage, webDriver, new DefaultElementLocatorFactory(webElement)), pageElement);
				}
				
				// Finally invoke the method on the instance of the class just created and manage the wait before/after around the method call
				waitWrapper.manageBefore();
				Object result = proxy.invoke(pageElement, args);
				waitWrapper.manageAfter();
				
				// If the result is the page element itself, return the proxy in place of to avoid proxying issues
				if (result == pageElement) {
					ReflectionUtils.copyByReflection(pageElement, obj);
					return obj;
				}
				else {
					return result;
				}
			} 
			catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e.getMessage(), e.getCause());
			}
		}
	}	
	
	/**
	 * Handler for the list of AbstractPageElement
	 */
	public static class LocatingElementListOfAbstractPageElementHandler implements InvocationHandler {
		/**
		 * Web driver reference
		 */
		private final WebDriver webDriver;
		
		/**
		 * A locator to find elements
		 */
		private final ElementLocator locator;
		
		/**
		 * The page element class to create the page element
		 */
		private final Class<? extends PageElement> pageElementClass;

		/**
		 * 
		 */
		private final IPageObject parentPage;
		
		/**
		 * Constructor 
		 * 
		 * @param pageObject The parent page object
		 * @param webDriver The web driver reference
		 * @param locator The locator to find the elements
		 * @param pageElementClass The page element real class
		 */
		public LocatingElementListOfAbstractPageElementHandler(IPageObject pageObject, WebDriver webDriver, ElementLocator locator, Class<? extends PageElement> pageElementClass) {
			this.webDriver = webDriver;
			this.pageElementClass = pageElementClass;
			this.locator = locator;
			this.parentPage = pageObject;
		}

		@Override
		public Object invoke(Object object, Method method, Object[] args) throws Throwable {
			List<WebElement> elems = locator.findElements();

			// Create new list to invoke method on later
			List<PageElement> list = new ArrayList<>();
				
			// For each elements found for the locator
			for (WebElement we : elems) {
				// Create the page element
				PageElement ape = proxyForAbstractPageElement(parentPage, webDriver, getClass().getClassLoader(), pageElementClass, we);

				// Inject the parent page object to the page element
				ReflectionUtils.injectPageObject(ape, parentPage);
					
				// Initialize the page element
				PageFactory.initElements(new PageDecorator(parentPage, webDriver, new DefaultElementLocatorFactory(we)), ape);

				// Store the page element into the list
				list.add(ape);
			}

			// Finally call the real methond on the list
			return method.invoke(list, args);
		}
	}
}
