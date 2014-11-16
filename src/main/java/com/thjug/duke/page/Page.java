package com.thjug.duke.page;

import org.jbehave.web.selenium.FluentWebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author nuboat
 */
public abstract class Page extends FluentWebDriverPage {

	public Page(final WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	protected WebDriver getWebDriver() {
		return getDriverProvider().get();
	}

	public void go(final String pageurl) {
		getWebDriver().get(pageurl);
	}

}
