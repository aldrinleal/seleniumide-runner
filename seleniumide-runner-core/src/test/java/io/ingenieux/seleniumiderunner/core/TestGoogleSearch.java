package io.ingenieux.seleniumiderunner.core;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class TestGoogleSearch {
  private WebDriverBackedSelenium selenium;

  private CaseRunnerBase runner;

  @Before
  public void setUp() throws Exception {
    String source = IOUtils.toString(getClass().getResourceAsStream("google-case.html"));

    runner = new HTMLCompiler(source).parse().getInstance(CaseRunnerBase.class);

    {
      final DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();

      String baseUrl = "https://google.com/";

      capabilities.setCapability("takesScreenshot", true);

      capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                                 which("phantomjs"));

      selenium = new WebDriverBackedSelenium(new PhantomJSDriver(capabilities), baseUrl);
    }

    runner.setSelenium(selenium);
    runner.setCtx(new SeleniumTestContext());
  }

  @After
  public void after() throws Exception {
    selenium.getWrappedDriver().manage().deleteAllCookies();
    selenium.getWrappedDriver().quit();
  }

  @Test
  public void testGoogleSearch() throws Exception {
    runner.execute();

    System.out.println(runner.getCtx());
  }

  private String which(String program) {
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

}
