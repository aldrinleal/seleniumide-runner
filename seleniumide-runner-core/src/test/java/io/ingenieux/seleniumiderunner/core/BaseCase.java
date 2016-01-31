package io.ingenieux.seleniumiderunner.core;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class BaseCase {
  WebDriverBackedSelenium selenium;

  CaseRunnerBase runner;

  protected WebDriverBackedSelenium getSelenium(String baseUrl) {
    final DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();

    capabilities.setCapability("takesScreenshot", true);

    capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                               which("phantomjs"));

    return new WebDriverBackedSelenium(new PhantomJSDriver(capabilities), baseUrl);
  }

  protected String which(String program) {
    String result = null;

    if (SystemUtils.IS_OS_WINDOWS) {
      program += ".cmd";
    }

    for (String k : System.getenv("PATH").split("\\Q" + File.pathSeparatorChar + "\\E")) {
      final File fullPath = new File(k, program);

      if (fullPath.exists())
        return fullPath.getAbsolutePath();
    }

    throw new IllegalStateException("Application " + program + " not found on path.");
  }

  @After
  public void after() throws Exception {
    if (null != selenium.getWrappedDriver()) {
      selenium.getWrappedDriver().manage().deleteAllCookies();
      selenium.getWrappedDriver().quit();
    }
  }

  protected void setUp(String caseName, String baseUrl) throws Exception {
    String source = IOUtils.toString(getClass().getResourceAsStream(caseName));

    runner = new HTMLCompiler(source).parse().getInstance(CaseRunnerBase.class);

    selenium = getSelenium(baseUrl);

    runner.setSelenium(selenium);
    runner.setCtx(new SeleniumTestContext());
  }
}
