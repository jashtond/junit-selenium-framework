package com.lotaris.selenium.page.user;

import com.lotaris.selenium.page.login.LoginWith;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Take care of the creation of the user to let the user available
 * to other rule.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class UserCreationRule implements TestRule {
	private static final Logger LOG = LoggerFactory.getLogger(UserCreationRule.class);
	
	private IUserGeneratorDecorator userGeneratorDecorator;
	
	/**
	 * User object to store credentials data
	 */
	private User user;

	/**
	 * User generator to create/delete user
	 */
	private IUserGenerator userGenerator;
	
	/**
	 * Constructor
	 */
	public UserCreationRule() {}
	
	/**
	 * Constructor
	 * 
	 * @param userGeneratorDecorator The user generator decorator
	 */
	public UserCreationRule(IUserGeneratorDecorator userGeneratorDecorator) {
		this.userGeneratorDecorator = userGeneratorDecorator;
	}
	
	/**
	 * @return The user created
	 */
	public User getCreatedUser() {
		return user;
	}
	
	@Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				before(description);
				try {
					base.evaluate();
				}
				finally {
					after(description);
				}
			}
		};
	}	
	
	/**
	 * Processing the generation the user
	 * 
	 * @param description The description of the test to get the login annotation
	 * @throws Throwable Any error occurred during the login process
	 */
	private void before(Description description) throws Throwable {
		// Check if a login annotation is present
		if (description.getAnnotation(LoginWith.class) != null) {
			LoginWith loginDescriptor = description.getAnnotation(LoginWith.class);
			
			// Need to generate a new user
			try {
				// Try to create a the user generator
				userGenerator = loginDescriptor.user().newInstance();
			
				// Decorate the user generator if a decorator is defined
				if (userGeneratorDecorator != null) {
					userGeneratorDecorator.decorate(userGenerator);
				}
				
				// Generate the user for the 
				ICreateStatement createStatement = new CreateBaseStatement(userGenerator);

				if (userGeneratorDecorator != null) {
					createStatement = userGeneratorDecorator.createStatement(createStatement);
				}

				user = createStatement.create();
			}
			catch (RuntimeException e) {
				LOG.error("Unable to create a user", e);
				throw new UserGenerationException("Unable to create a new user.", e.getCause());
			}
		}
	}
	
	/**
	 * Process the user cleanup
	 * 
	 * @param description The description of a test
	 */
	private void after(Description description) throws UserGenerationException {
		// Check if a login occurred
		if (description.getAnnotation(LoginWith.class) != null) {
			// Cleanup the data generated if necessary
			if (userGenerator != null) {
				IDeleteStatement deleteStatement = new DeleteBaseStatement(userGenerator);
				
				if (userGeneratorDecorator != null) {
					deleteStatement = userGeneratorDecorator.deleteStatement(deleteStatement);
				}
				
				deleteStatement.delete(user);
			}
		}
	}
}
