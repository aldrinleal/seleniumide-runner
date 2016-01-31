package io.ingenieux.seleniumiderunner.core;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CaseRunnerBase {
  /**
   * Logger Instance
   */
  protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

  /**
   * Title Timeout (seconds) *
   */
  protected long defaultTitleTimeout = 30;

  public long getDefaultTitleTimeout() {
    return defaultTitleTimeout;
  }

  public void setDefaultTitleTimeout(long defaultTitleTimeout) {
    this.defaultTitleTimeout = defaultTitleTimeout;
  }

  protected WebDriverBackedSelenium selenium;

  public WebDriverBackedSelenium getSelenium() {
    return selenium;
  }

  public void setSelenium(WebDriverBackedSelenium selenium) {
    this.selenium = selenium;
  }

  public void waitForTitle(WebDriverBackedSelenium selenium, final String title) {
    Wait<WebDriver> wait = new WebDriverWait(selenium.getWrappedDriver(), defaultTitleTimeout);

    wait.until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver webDriver) {
        return webDriver.getTitle().equalsIgnoreCase(title) ? Boolean.TRUE : null;
      }
    });
  }

  protected SeleniumTestContext ctx;

  public SeleniumTestContext getCtx() {
    return ctx;
  }

  public void setCtx(SeleniumTestContext ctx) {
    this.ctx = ctx;
  }

  public abstract void execute() throws Exception;
}
