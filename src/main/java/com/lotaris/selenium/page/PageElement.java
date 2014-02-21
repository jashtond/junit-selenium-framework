package com.lotaris.selenium.page;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

/**
 * Abstract class implementing WebElement, WrapsElement and Locatable
 * interfaces. This class is the class to extend in order to allow the
 * PageElementFactory to go and build a page element automatically with the
 * information provided by the annotations.
 *
 * @see PageElementFactory
 *
 * @author Alain Lala <alain.lala@lotaris.com>
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class PageElement<T extends IPageObject> extends PageBlock implements WebElement, WrapsElement, Locatable {
	/**
	 * The web element that the abstract page element is proxying
	 */
	private WebElement webElement;

	/**
	 * The parent page object
	 */
	private T pageObject;
	
	/**
	 * Constructor
	 * 
	 * @param webDriver The webdriver
	 */
	public PageElement(WebDriver webDriver) { 
		super(webDriver);
	}
	
	/**
	 * Constructor
	 * 
	 * @param webDriver The webdriver
	 * @param webElement Webelement manipulated by the page element
	 */
	public PageElement(WebDriver webDriver, WebElement webElement) {
		super(webDriver);
		this.webElement = webElement;
	}

	@Override
	void protectedBuild(WebDriver webDriver) {}
	
	/**
	 * @return Retrieve the web element
	 */
	WebElement getWebelement() {
		return webElement;
	}
	
	/**
	 * @return The page object
	 */
	public T getPageObject() {
		if (pageObject == null) {
			throw new IllegalStateException("Page object should be injected by the framework.");
		}
		return pageObject;
	}
	
	//<editor-fold defaultstate="collapsed" desc="Default Overrides">
	@Override
	public void click() {
		webElement.click();
	}

	@Override
	public void submit() {
		webElement.submit();
	}

	@Override
	public void sendKeys(CharSequence... css) {
		webElement.sendKeys(css);
	}

	@Override
	public void clear() {
		webElement.clear();
	}

	@Override
	public String getTagName() {
		return webElement.getTagName();
	}

	@Override
	public String getAttribute(String string) {
		return webElement.getAttribute(string);
	}

	@Override
	public boolean isSelected() {
		return webElement.isSelected();
	}

	@Override
	public boolean isEnabled() {
		return webElement.isEnabled();
	}

	@Override
	public String getText() {
		return webElement.getText();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return webElement.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return webElement.findElement(by);
	}

	@Override
	public boolean isDisplayed() {
		return webElement.isDisplayed();
	}

	@Override
	public Point getLocation() {
		return webElement.getLocation();
	}

	@Override
	public Dimension getSize() {
		return webElement.getSize();
	}

	@Override
	public String getCssValue(String string) {
		return webElement.getCssValue(string);
	}

	@Override
	public WebElement getWrappedElement() {
		return webElement;
	}

	@Override
	public Coordinates getCoordinates() {
		return ((Locatable) webElement).getCoordinates();
	}
	//</editor-fold>
}
