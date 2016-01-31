package io.ingenieux.seleniumiderunner.core;

import org.junit.Before;
import org.junit.Test;

public class TestOracleJavaVersion extends BaseCase {
  @Before
  public void setUp() throws Exception {
    setUp("oracle-java-version.html", "http://www.oracle.com/technetwork/java/javase/downloads/index.html");
  }

  @Test
  public void testGoogleSearch() throws Exception {
    runner.execute();

    System.out.println(runner.getCtx());
  }
}
