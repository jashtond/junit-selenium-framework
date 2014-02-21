package com.lotaris.selenium.page.login;

import com.lotaris.selenium.page.user.UserCreationRule;
import com.lotaris.selenium.page.user.User;
import com.lotaris.selenium.IConfiguration;
import com.lotaris.selenium.driver.WebDriverUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rule to manage as most as possible the login/logout for a 
 * selenium test.
 * 
 * The idea is to avoid the boiler plate code that is required
 * to a login and/or logout for each test
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 * 
 * @param <AFTER_LOGIN_PAGE> The login page type
 * @param <AFTER_LOGOUT_PAGE> The logout page type
 */
public class LoginRule<AFTER_LOGIN_PAGE extends ILogoutPage, AFTER_LOGOUT_PAGE extends ILoginPage> implements TestRule {
	private static final Logger LOG = LoggerFactory.getLogger(LoginRule.class);
	
	/**
	 * The configuration
	 */
	private IConfiguration configuration;
	
	/**
	 * The login page class, this class allow to create the login page
	 * which is used to do the login process.
	 */
	private Class<? extends ILoginPage> loginPageClass;
	
	/**
	 * The login page object
	 */
	private ILoginPage loginPageObject;
	
	/**
	 * The page objects obtained after login/logout
	 */
	private AFTER_LOGIN_PAGE pageObjectAfterLogin;

	/**
	 * Login descriptor to have the configuration for the rule
	 */
	private LoginWith loginDescriptor; 
	
	/**
	 * Reference to the user creation rule
	 */
	private UserCreationRule userCreationRule;
	
	/**
	 * Constructor
	 * 
	 * @param userCreationRule The user creation rule
	 * @param loginPageClass The login page class to instantiate for the login process
	 */
	public LoginRule(IConfiguration configuration, UserCreationRule userCreationRule, Class<? extends ILoginPage> loginPageClass) {
		this.userCreationRule = userCreationRule;
		this.loginPageClass = loginPageClass;
		this.configuration = configuration;
	}
	
	/**
	 * @return The user consolidated after the generation
	 */
	public User getLoggedInUser() {
		return userCreationRule.getCreatedUser();
	}
	
	/**
	 * @return The page obtained after the login
	 */
	public AFTER_LOGIN_PAGE getPageObject() {
		return pageObjectAfterLogin;
	}
	
	@Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				before(description);
				base.evaluate();
				after();
			}
		};
	}	
	
	/**
	 * Processing the login of a generated user
	 * 
	 * @param description The description of the test to get the login annotation
	 * @throws Throwable Any error occurred during the login process
	 */
	private void before(Description description) throws Throwable {
		// Check if a login annotation is present
		if (description.getAnnotation(LoginWith.class) != null) {
			loginDescriptor = description.getAnnotation(LoginWith.class);
			
			// Do the login and get the page just after the login
			loginPageObject = WebDriverUtils.startFromBaseUrl(configuration, loginPageClass);
			loginPageObject.checks();
			User user = userCreationRule.getCreatedUser();
			pageObjectAfterLogin = (AFTER_LOGIN_PAGE) loginPageObject.login(user.getUser(), user.getPassword());
		}
	}
	
	/**
	 * Process the logout
	 */
	private void after() {
		// Check if a login occurred
		if (loginDescriptor != null) {
			// Check if a logout is required
			if (loginDescriptor.logout() && loginPageObject != null) {
				// Do the logout and checks
				pageObjectAfterLogin.logout().checks();
			}
		}
	}
}
