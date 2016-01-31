package io.ingenieux.seleniumiderunner.core;

import org.junit.Before;
import org.junit.Test;

public class TestGoogleSearch extends BaseCase {
  @Before
  public void setUp() throws Exception {
    setUp("google-case.html", "http://google.com/");
  }

  @Test
  public void testGoogleSearch() throws Exception {
    runner.execute();

    System.out.println(runner.getCtx());
  }

}
