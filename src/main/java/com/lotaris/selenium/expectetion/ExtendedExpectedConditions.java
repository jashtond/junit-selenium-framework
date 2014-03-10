package com.lotaris.selenium.expectetion;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * More conditions for Selenium wait mechanism
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class ExtendedExpectedConditions {
	/**
	 * Condition to check if all elements located by the locator are hidden
	 * 
	 * @param locator The locator rule
	 * @return The conditions
	 */
	public static ExpectedCondition<Boolean> invisibilityOfAllElementLocated(final By locator) {
		return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        try {
					// Find all elements corresponding to the locator
					List<WebElement> elems = driver.findElements(locator);
          
					// No elems, considered as all elems are hidden
					if (elems == null || elems.isEmpty()) {
						return true;
					}
					
					// If only one of the elems is visible, fail the check
					for (WebElement elem : elems) {
						if (elem.isDisplayed()) {
							return false;
						}
					}
					
					// All element are hidden
					return true;
        } 
				catch (NoSuchElementException | StaleElementReferenceException e) {
          // Returns true because the element is not present in DOM. The
          // try block checks if the element is present but is invisible.
          // Returns true because stale element reference implies that element
          // is no longer visible.
          return true;
        }
      }
    };
  }
	
	/**
	 * Condition to check that an element is no present in the DOM
	 * 
	 * @param locator The locator to find the element
	 * @return The condition
	 */
	public static ExpectedCondition<Boolean> absenceOfElementLocated(final By locator) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				try {
					input.findElement(locator);
					return false;
				}
				catch (NoSuchElementException | StaleElementReferenceException e) {
					return true;
				}
			}
		};
	}
	
	/**
	 * Condition to check that at least one element is visible on the page
	 * 
	 * @param locator The locator to find the element
	 * @return The condition
	 */
	public static ExpectedCondition<Boolean> atLeastOneElementIsVisible(final By locator) {
		return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        try {
					// Find the elements
					List<WebElement> elems = driver.findElements(locator);
          
					// If no element are found, considered as true
					if (elems == null || elems.isEmpty()) {
						return true;
					}
					
					// Return true at the first element that is displayed
					for (WebElement elem : elems) {
						if (elem.isDisplayed()) {
							return true;
						}
					}
					
					// No element found is displayed
					return false;
        } 
				catch (NoSuchElementException | StaleElementReferenceException e) {
          // Returns true because the element is not present in DOM. The
          // try block checks if the element is present but is invisible.
          // Returns true because stale element reference implies that element
          // is no longer visible.
          return false;
        }
      }
    };
  }
}
