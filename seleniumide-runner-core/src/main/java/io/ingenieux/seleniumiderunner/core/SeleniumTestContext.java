package io.ingenieux.seleniumiderunner.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.Map;
import java.util.TreeMap;

public class SeleniumTestContext {
  public Map<String, String> vars = new TreeMap<String, String>();

  public StrSubstitutor strSubstitutor = new StrSubstitutor(StrLookup.mapLookup(vars));

  public SeleniumTestContext() {
  }

  public String getValue(String k) {
    return StringUtils.defaultString(vars.get(k));
  }

  public void setValue(String k, String v) {
    vars.put(k, v);
  }

  public String evaluate(String text) {
    return strSubstitutor.replace(text);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("vars", vars)
        .toString();
  }
}