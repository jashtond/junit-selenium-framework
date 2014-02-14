package com.lotaris.selenium.rule;

import com.lotaris.selenium.IConfiguration;
import com.lotaris.selenium.driver.WebDriverUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * The JUnit rules take can help to take screenshots when we want to see what is happening in a test. Especially when a test is failing.
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class ScreenShotRule implements MethodRule {

	public static final String SEPARATOR = "_";

	private IConfiguration configuration;
	
	public ScreenShotRule(IConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				try {
					statement.evaluate();
				} 
				catch (Throwable t) {
					WebDriverUtils.takeScreenshot(configuration, frameworkMethod.getName() + SEPARATOR + Calendar.getInstance().getTime());

					if (t.getMessage() != null && t.getMessage().contains(InvocationTargetException.class.getCanonicalName())) {
						throw t.getCause().getCause();
					} 
					else {
						throw t;
					}
				}
			}
		};
	}
}
