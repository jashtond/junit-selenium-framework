package com.lotaris.selenium.wait;

import com.lotaris.selenium.page.IPageBlock;
import com.lotaris.selenium.utils.ReflectionUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The wait wrapper takes care of resolving the
 * wait conditions present in the annotations for the
 * methods of page blocks.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class WaitWrapper {
	/**
	 * Selenium wait to run conditions
	 */
	private WebDriverWait webDriverWait;

	/**
	 * Page block that contains the elements to check conditions
	 */
	private IPageBlock pageBlock;
	
	/**
	 * Method for which the wait is done
	 */
	private Method method;
	
	/**
	 * The type of waiter
	 */
	private Waiter waiterType;
	
	/**
	 * Wait must be done before the execution of the method
	 */
	private boolean before = true;
	
	/**
	 * Constructor
	 * 
	 * @param pageBlock Page block
	 * @param webDriverWait Web driver wait
	 * @param method The method for which the wait is asked
	 */
	public WaitWrapper(IPageBlock pageBlock, WebDriverWait webDriverWait, Method method) {
		// Retrieve the number of annotation present on the method
		int annotationPresent = Waiter.numberOfAnnotationPresent(method);
		
		// Check that no more than one wait annotation is present on the method
		if (annotationPresent > 1) {
			throw new IllegalStateException("Only one Wait annotation can be present on a method");
		}
		
		// Verify the method is annotated with an eligible one
		if (annotationPresent == 1) {
			this.webDriverWait = webDriverWait;
			this.pageBlock = pageBlock;
			this.method = method;

			// Retrive the method name
			String methodName = this.method.getName();
			
			// Wait method has before and after wait markers
			if (WaitMarker.BEFORE.match(methodName) && WaitMarker.AFTER.match(methodName)) {
				throw new IllegalStateException(
					"The method " + methodName + " on " + this.method.getDeclaringClass() + " is annotated with @Wait but its name is incorrect. "
					+ "The method name should start by <waitAnd> OR end by <AndWait> but currently contains <waitAnd><methodName><AndWait>. The"
					+ "enforcement of the name is important to define if the wait is done before or after the method execution. If the method name "
					+ "starts with <waitAnd>, it means that the wait is done before method execution. If the method name ends with <AndWait>, it means "
					+ "that the wait is done after the method execution.");
			}

			// Wait method has no wait markers
			else if (!WaitMarker.BEFORE.match(methodName) && !WaitMarker.AFTER.match(methodName)) {
				throw new IllegalStateException(
					"The method " + methodName + " on " + this.method.getDeclaringClass() + " is annotated with @Wait but its name is incorrect. "
					+ "The method name should start by <waitAnd> OR end by <AndWait> but currently contains only <methodName>. The"
					+ "enforcement of the name is important to define if the wait is done before or after the method execution. If the method name "
					+ "starts with <waitAnd>, it means that the wait is done before method execution. If the method name ends with <AndWait>, it means "
					+ "that the wait is done after the method execution.");
			}			
			
			// Check if the wait must be done before or after
			before = WaitMarker.BEFORE.match(methodName);

			// Retrieve the waiter type
			waiterType = Waiter.retrieve(this.method);
		}
	}

	/**
	 * Manage the wait to be done before executing a method.
	 * It takes care to check if the wait must be done or not.
	 */
	public void manageBefore() {
		if (waiterType != null && before) {
			waiterType.wait(this);
		}
	}
	
	/**
	 * Manage the wait to be done after executing a method
	 * It takes care to check if the wait must be done or not.
	 */
	public void manageAfter() {
		if (waiterType != null && !before) {
			waiterType.wait(this);
		}
	}

	/**
	 * Extracts a field from a page block object
	 * 
	 * @param pageBlock The page block object from where to extract the field
	 * @param fieldName The field name to extract
	 * @return The field extracted
	 * @throws IllegalArgumentException Thrown by java reflection utils
	 * @throws NoSuchFieldException Thrown by java reflection utils
	 * @throws IllegalAccessException Thrown by java reflection utils
	 */
	private static WebElement extractField(Object pageBlock, String fieldName) throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		// Retrieve the field from a the class hierarchy of the page block class
		Field field = ReflectionUtils.findField(fieldName, pageBlock.getClass());

		// Check that a field is retrieved
		if (field != null) {
			// Modify the visibility if required
			boolean acc = false;
			if (!field.isAccessible()) {
				field.setAccessible(true);
				acc = true;
			}

			// Retrieve the value of the page block field
			Object result = field.get(pageBlock);

			// Restore the accessibility if required
			if (acc) {
				field.setAccessible(false);
			}

			// Return the web element
			try {
				return (WebElement) result;
			}
			catch (ClassCastException cce) {
				throw new RuntimeException("The annotation refers to a field that is not from type " 
					+ WebElement.class.getCanonicalName() + ". Only WebElements are authorized to be configured in this annotation.", cce);
			}
		}
		else {
			throw new RuntimeException("The annotation refers to the field " + fieldName + " that does not exist in " 
				+ pageBlock.getClass().getCanonicalName() + " or in its super classes.");
		}
	}	

	/**
	 * Invisibility class condition to check an element is invisible
	 */
	static class InvisibilityOfElement implements ExpectedCondition<Boolean> {
		// Page element to check
		private WebElement webElement;
		
		/**
		 * Constructor
		 * 
		 * @param webElement Web element to check
		 */
		public InvisibilityOfElement(WebElement webElement) {
			this.webElement = webElement;
		}
		
		@Override
		public Boolean apply(WebDriver input) {
			try {
				return !webElement.isDisplayed();
			}
			catch (NullPointerException | NoSuchElementException | StaleElementReferenceException e) {
				return true;
			}
		}
	}

	/**
	 * Enumeration that represent the core logic of each
	 * wait annotation type and that is hidden to the framework
	 * users.
	 * 
	 * It's there to offer the possibility to extend more easily with new annotations
	 * in case of new wait conditions facilitators must be added.
	 */
	private enum Waiter {
		/**
		 * The standard wait conditions that can be use for everything
		 */
		WAIT(Wait.class) {
			@Override
			protected void doWait(WaitWrapper wrapper) {
				// Retrive the annotation
				Wait wait = getWaitAnnotationClass(wrapper);

				// Try to build a new instance of the condition by getting a constructor from pageblock class (or related class)
				IWaitCondition waitCondition = null;
				try {
					Constructor constructor = ReflectionUtils.recurseFindConstructor(wait.value(), wrapper.pageBlock.getClass());

					// If constructor found, use it otherwise use empty constructor if available
					if (constructor != null) {
						waitCondition = (IWaitCondition) constructor.newInstance(wrapper.pageBlock);
					}
					else {
						waitCondition = wait.value().newInstance();
					}
				}
				catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(
						"Unable to create a new instance of the expected condtion of class " + wait.value().getCanonicalName() + ". "
						+ "An empty constructor is probably missing or a constructor " + wait.value().getCanonicalName() + "(" + wrapper.pageBlock.getClass().getCanonicalName() + ") "
						+ "is not defined on the expected condition class. Only these two constructors are possible. The constructor with page block is looked up first then the "
						+ "empty constructor.", e);
				}

				try {
					// Finally, wait until the condition match or timeout reached
					wrapper.webDriverWait.until(waitCondition.build());
				}
				catch (Throwable t) {
					LOG.error("Unable to accomplish the wait condition of type " + 
						wait.value().getCanonicalName() + " during the wait for " + methodFullName(wrapper));
					throw t;
				}
			}
		},
		
		/**
		 * The wait for visibility of an element
		 */
		WAIT_VISIBILITY(WaitVisibility.class) {
			@Override
			protected void doWait(WaitWrapper wrapper) {
				// Retrieve annotation
				WaitVisibility waitVisibility = getWaitAnnotationClass(wrapper);
				
				try {
					try {
						// Check if the condition should be inversed or not
						if (waitVisibility.not()) {
							// Wait for invisibility of an element
							wrapper.webDriverWait.until(new InvisibilityOfElement(extractField(wrapper.pageBlock, waitVisibility.value())));
						}
						else {
							// Wait for visibility
							wrapper.webDriverWait.until(ExpectedConditions.visibilityOf(extractField(wrapper.pageBlock, waitVisibility.value())));
						}
					}
					catch (Throwable t) {
						LOG.error("Unable to wait the " + (waitVisibility.not() ? "invisibility" : "visibility") + " of element referenced by " + 
							waitVisibility.value() + " during the wait for " + methodFullName(wrapper));
						throw t;
					}
				}
				catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
					throw new RuntimeException("Unable to get the WebElement " + waitVisibility.value() + ". Be sure the class " + 
						wrapper.pageBlock.getClass().getCanonicalName() + " has the field declared.", e);
				}
			}
		},
		
		/**
		 * The wait for staleness of an element
		 */
		WAIT_STALENESS(WaitStaleness.class) {
			@Override
			protected void doWait(WaitWrapper wrapper) {
				// Retrieve the annotation
				WaitStaleness waitStaleness = getWaitAnnotationClass(wrapper);
				
				try {
					// Build the base condition
					ExpectedCondition<Boolean> stalenessCondition = ExpectedConditions.stalenessOf(extractField(wrapper.pageBlock, waitStaleness.value()));

					// Check if it should be inversed
					try {
						if (waitStaleness.not()) {
							// Wait for un-staleness
							wrapper.webDriverWait.until(ExpectedConditions.not(stalenessCondition));
						}
						else {
							// Wait for staleness
							wrapper.webDriverWait.until(stalenessCondition);
						}
					}
					catch (Throwable t) {
						LOG.error("Unable to wait the " + (waitStaleness.not() ? "un-staleness" : "staleness") + " of the element refered by " + 
							waitStaleness.value() + " during the wait for " + methodFullName(wrapper));
						throw t;
					}
				}
				catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
					throw new RuntimeException("Unable to get the WebElement " + waitStaleness.value() + ". Be sure the class " + 
						wrapper.pageBlock.getClass().getCanonicalName() + " has the field declared.", e);
				}			
			}
		};
		
		private static final Logger LOG = LoggerFactory.getLogger(Waiter.class);

		// Annotation class
		private Class<? extends Annotation> waitAnnotationClass;
		
		/**
		 * Constructor
		 * 
		 * @param waitAnnotationClass Wait annotation class
		 */
		private Waiter(Class<? extends Annotation> waitAnnotationClass) {
			this.waitAnnotationClass = waitAnnotationClass;
		}
		
		/**
		 * Retrieve the instance of annotation of the right type
		 * 
		 * @param <T> Type of the annotation
		 * @param wrapper The wait wrapper to get the data required to retrieve method
		 * @return The instance of the annotation
		 */
		protected <T extends Annotation> T getWaitAnnotationClass(WaitWrapper wrapper) {
			return (T) wrapper.method.getAnnotation(waitAnnotationClass);
		}
		
		/**
		 * Execute the wait operations
		 * 
		 * @param wrapper The wait wrapper to handle
		 */
		protected void wait(WaitWrapper wrapper) {
			log(wrapper);
			doWait(wrapper);
		}
		
		/**
		 * Internal wait execution
		 * 
		 * @param wrapper The wait wrapper to handle
		 */
		protected abstract void doWait(WaitWrapper wrapper);
		
		/**
		 * Retrieve the number of annotations configured on a method
		 * 
		 * @param method The method to check
		 * @return The number of annotations present
		 */
		protected static int numberOfAnnotationPresent(Method method) {
			int annotationPresent = 0;
			
			// Count the annotations
			for (Waiter w : Waiter.values()) {
				if (method.isAnnotationPresent(w.waitAnnotationClass)) {
					annotationPresent++;
				}
			}
			
			return annotationPresent;
		}
		
		/**
		 * Retrieve the wait annotation type based on the method. Only
		 * the first annotation is returned.
		 * 
		 * @param method The method to check
		 * @return The type found, null otherwise
		 */
		protected static Waiter retrieve(Method method) {
			// Find the wait type
			for (Waiter w : Waiter.values()) {
				if (method.isAnnotationPresent(w.waitAnnotationClass)) {
					return w;
				}
			}
			return null;
		}
		
		/**
		 * Log information for the wait condition run
		 * 
		 * @param wrapper The wrapper to get info
		 */
		private void log(WaitWrapper wrapper) {
			LOG.trace("Waiting action: {} - {} - {}", 
				wrapper.before ? "BEFORE" : "AFTER", this.name(), methodFullName(wrapper));
		}
		
		/**
		 * Extract method name from the wrapper
		 * 
		 * @param wrapper The wrapper to get info
		 * @return The method name extracted
		 */
		private static String methodFullName(WaitWrapper wrapper) {
			return wrapper.method.getDeclaringClass().getCanonicalName() + "." + wrapper.method.getName();
		}
	}
	
	/**
	 * Wait marker to know if a method name follow the code conventions
	 * that modify the behavior of the waiting process.
	 */
	private enum WaitMarker {
		/**
		 * Check to define if the wait is done before
		 */
		BEFORE("waitAnd", "waitUntil") {
			@Override
			protected boolean match(String methodName) {
				for (String marker : getMarkers()) {
					if (methodName.startsWith(marker)) {
						return true;
					}
				}
				return false;
			}
		},
		
		/**
		 * Check to define if the wait is done after
		 */
		AFTER("AndWait") {
			@Override
			protected boolean match(String methodName) {
				for (String marker : getMarkers()) {
					if (methodName.endsWith(marker)) {
						return true;
					}
				}
				return false;
			}
		};
		
		private String[] markers;
		
		/**
		 * Constructor
		 * 
		 * @param markers The accepted markers for a marker type
		 */
		private WaitMarker(String ... markers) {
			this.markers = markers;
		}
		
		/**
		 * @return Retrieve the list of markers
		 */
		protected String[] getMarkers() {
			return markers;
		}
		
		/**
		 * Check if a method name match the coding conventions
		 * 
		 * @param methodName The method name to check
		 * @return True if the method match the conventions
		 */
		protected abstract boolean match(String methodName);
	}
}
