package io.ingenieux.seleniumiderunner.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translate Statements from html to Java
 */
public class StatementGenerator {
  private static final Logger LOGGER = LoggerFactory.getLogger(StatementGenerator.class);

  static final String EVALUATE_REF = "ctx.evaluate";

  static final String GETVALUE_REF = "ctx.getValue";

  static final String SETVALUE_REF = "ctx.setValue";

  protected void translate() throws Exception {

  }

  public boolean matchesAny(String value, String... others) {
    for (String x : others)
      if (x.equals(value))
        return true;

    return false;
  }

  public void translate(String param1, String param2, String param3,
                        StringBuilder sb) {
    if (param1.equals("waitForTitle")) {
      sb.append("waitForTitle(selenium, " + EVALUATE_REF + "(\"");
      sb.append(param2);
      sb.append("\"));");
    } else if (matchesAny(param1, "addSelection", "clickAt", "keyPress", "openWindow", "select", "type", "typeKeys", "waitForPopUp")) {
      sb.append("selenium.");
      sb.append(param1);
      sb.append("(\"");
      sb.append(param2);
      sb.append("\", " + EVALUATE_REF + "(\"");
      sb.append(param3);
      sb.append("\"));");
    } else if (matchesAny(param1, "assertAlert", "assertNotAlert")) {
      if (param1.equals("assertAlert")) {
        sb.append("assertEquals");
      } else if (param1.equals("assertNotAlert")) {
        sb.append("assertNotEquals");
      }

      sb.append("(\"");
      sb.append(param2);
      sb.append("\", selenium.getAlert());");
    } else if (matchesAny(param1, "assertChecked", "assertNotChecked")) {
      if (param1.equals("assertChecked")) {
        sb.append("assertTrue");
      } else if (param1.equals("assertNotChecked")) {
        sb.append("assertFalse");
      }

      sb.append("(selenium.isChecked(\"");
      sb.append(param2);
      sb.append("\"));");
    } else if (matchesAny(param1, "assertLocation", "assertNotLocation")) {
      if (param1.equals("assertLocation")) {
        sb.append("assertEquals");
      } else if (param1.equals("assertNotLocation")) {
        sb.append("assertNotEquals");
      }

      sb.append("(\"");
      sb.append(param2);
      sb.append("\", selenium.getLocation());");
    } else if (matchesAny(param1, "assertElementNotPresent", "assertElementPresent")) {

      if (param1.equals("assertElementNotPresent")) {
        sb.append("assertFalse");
      } else if (param1.equals("assertElementPresent")) {
        sb.append("assertTrue");
      }

      sb.append("(selenium.isElementPresent(\"");
      sb.append(param2);
      sb.append("\"));");
    } else if (matchesAny(param1, "assertNotPartialText", "assertPartialText")) {

      if (param1.equals("assertNotPartialText")) {
        sb.append("assertFalse");
      } else if (param1.equals("assertPartialText")) {
        sb.append("assertTrue");
      }

      sb.append("(selenium.isPartialText(\"");
      sb.append(param2);
      sb.append("\", ");

      if (param3.startsWith("${")) {
        sb.append("" + GETVALUE_REF + "(\"");

        String text = param3.substring(2, param3.length() - 1);

        sb.append(text);
        sb.append("\")");
      } else {
        sb.append("\"");
        sb.append(param3);
        sb.append("\"");
      }

      sb.append("));");
    } else if (matchesAny(param1, "assertNotSelectedLabel", "assertSelectedLabel")) {

      if (param1.equals("assertNotSelectedLabel")) {
        sb.append("assertNotEquals");
      } else if (param1.equals("assertSelectedLabel")) {
        sb.append("assertEquals");
      }

      sb.append("(\"");
      sb.append(param3);
      sb.append("\", selenium.getSelectedLabel(\"");
      sb.append(param2);
      sb.append("\"));");
    } else if (matchesAny(param1, "assertNotSelectedLabels", "assertSelectedLabels")) {

      if (param1.equals("assertNotSelectedLabels")) {
        sb.append("assertNotEquals");
      } else if (param1.equals("assertSelectedLabels")) {
        sb.append("assertEquals");
      }

      sb.append("(\"");
      sb.append(param3);
      sb.append("\", join(selenium.getSelectedLabels(\"");
      sb.append(param2);
      sb.append("\"), \',\'));");
    } else if (matchesAny(param1, "assertNotText", "assertText")) {

      if (param1.equals("assertNotText")) {
        sb.append("assertNotEquals");
      } else if (param1.equals("assertText")) {
        sb.append("assertEquals");
      }

      sb.append("(" + EVALUATE_REF + "(\"");
      sb.append(param3);
      sb.append("\"), selenium.getText(\"");
      sb.append(param2);
      sb.append("\"));");
    } else if (matchesAny(param1, "assertNotValue", "assertValue")) {

      if (param1.equals("assertNotValue")) {
        sb.append("assertNotEquals");
      } else if (param1.equals("assertValue")) {
        sb.append("assertEquals");
      }

      sb.append("(\"");
      sb.append(param3);
      sb.append("\", selenium.getValue(\"");
      sb.append(param2);
      sb.append("\"));");
    } else if (matchesAny(param1, "assertNotVisible", "assertVisible")) {
      if (param1.equals("assertNotVisible")) {
        sb.append("assertFalse");
      } else if (param1.equals("assertVisible")) {
        sb.append("assertTrue");
      }

      sb.append("(");
      sb.append("selenium.isVisible(\"");
      sb.append(param2);
      sb.append("\"));");
//		} else if (param1.equals("assertSelectOptions")) {
//			String[] expectedArray = StringUtil.split(param3);
//
//			sb.append("String[] actualArray = ");
//			sb.append("selenium.getSelectOptions(\"");
//			sb.append(param2);
//			sb.append("\");");
//
//			sb.append("assertEquals(");
//			sb.append(expectedArray.length);
//			sb.append(", actualArray.length);");
//
//			for (int i = 0; i < expectedArray.length; i++) {
//				sb.append("assertEquals(\"");
//				sb.append(expectedArray[i]);
//				sb.append("\", actualArray[");
//				sb.append(i);
//				sb.append("]);");
//			}
    } else if (matchesAny(param1, "assertTextNotPresent", "assertTextPresent")) {

      if (param1.equals("assertTextNotPresent")) {
        sb.append("assertFalse");
      } else if (param1.equals("assertTextPresent")) {
        sb.append("assertTrue");
      }

      sb.append("(selenium.isTextPresent(\"");
      sb.append(param2);
      sb.append("\"));");
//		} else if (param1.equals("captureEntirePageScreenshot")) {
//			int pos = param2.lastIndexOf("\\");
//
//			String dirName = param2.substring(0, pos + 1);
//
//			sb.append("FileUtil.mkdirs(" + EVALUATE_REF + "(\"");
//			sb.append(dirName);
//			sb.append("\"));");
//			sb.append("selenium.captureEntirePageScreenshot(");
//			sb.append("" + EVALUATE_REF + "(\"");
//			sb.append(param2);
//			sb.append("\"), \"\");");
    } else if (matchesAny(param1, "check", "click", "doubleClick", "mouseDown", "mouseOver", "mouseUp", "open", "uncheck", "selectFrame", "selectWindow")) {

      sb.append("selenium.");
      sb.append(param1);
      sb.append("(\"");
      sb.append(param2);
      sb.append("\");");
    } else if (matchesAny(param1, "clickAndWait")) {
      sb.append("selenium.click(" + EVALUATE_REF + "(\"");
      sb.append(param2);
      sb.append("\"));");
      sb.append("selenium.waitForPageToLoad(\"30000\");");
    } else if (matchesAny(param1, "clickAtAndWait", "keyPressAndWait", "selectAndWait")) {
      sb.append("selenium.");

      String text = param1.substring(0, param1.length() - 7);

      sb.append(text);
      sb.append("(\"");
      sb.append(param2);
      sb.append("\", " + EVALUATE_REF + "(\"");
      sb.append(param3);
      sb.append("\"));");
      sb.append("selenium.waitForPageToLoad(\"30000\");");
    } else if (matchesAny(param1, "close", "refresh")) {
      sb.append("selenium.");
      sb.append(param1);
      sb.append("();");
    } else if (matchesAny(param1, "dragAndDropToObject")) {
      sb.append("selenium.");
      sb.append("dragAndDropToObject(\"");
      sb.append(param2);
      sb.append("\", \"");
      sb.append(param3);
      sb.append("\");");
    } else if (matchesAny(param1, "echo")) {
      sb.append("System.out.println(\"");
      sb.append(param2);
      sb.append("\");");
//		} else if (param1.equals("gotoIf")) {
//			String conditional = StringUtil.replace(param2, new String[] {
//					"${", "}" }, new String[] { "", "" });
//
//			sb.append("if (");
//			sb.append(conditional);
//			sb.append(") {");
//			sb.append("label =");
//			sb.append(labels.get(param3));
//			sb.append(";");
//			sb.append("continue;");
//			sb.append("}");
//		} else if (param1.equals("label")) {
//			String label = labels.get(param2);
//
//			sb.append("case ");
//			sb.append(label);
//			sb.append(":");
    } else if (matchesAny(param1, "pause")) {
      sb.append("Thread.sleep(");
      sb.append(param2);
      sb.append(");");
    } else if (matchesAny(param1, "refreshAndWait")) {
      sb.append("selenium.refresh();");
      sb.append("selenium.waitForPageToLoad(\"30000\");");
    } else if (matchesAny(param1, "store")) {
      sb.append("boolean ");
      sb.append(param3);
      sb.append(" = ");

      if (param2.startsWith("eval(")) {
        String eval = param2.substring(5, param2.length() - 1);

        eval = eval.replace("\'", "\""); // StringUtil.replace(eval, "'", "\"");

        sb.append(eval);
      }

      sb.append(";");
    } else if (matchesAny(param1, "storeIncrementedText")) {
      sb.append("String ");
      sb.append(param3);
      sb.append(" = selenium.getIncrementedText(\"");
      sb.append(param2);
      sb.append("\");");

      sb.append("" + SETVALUE_REF + "(\"");
      sb.append(param3);
      sb.append("\", ");
      sb.append(param3);
      sb.append(");");
    } else if (matchesAny(param1, "storeText")) {
      sb.append("String ");
      sb.append(param3);
      sb.append(" = selenium.getText(\"");
      sb.append(param2);
      sb.append("\");");

      sb.append("" + SETVALUE_REF + "(\"");
      sb.append(param3);
      sb.append("\", ");
      sb.append(param3);
      sb.append(");");
    } else if (matchesAny(param1, "verifyElementNotPresent", "verifyElementPresent")) {

      if (param1.equals("verifyElementNotPresent")) {
        sb.append("verifyFalse");
      } else if (param1.equals("verifyElementPresent")) {
        sb.append("verifyTrue");
      }

      sb.append("(selenium.isElementPresent(\"");
      sb.append(param2);
      sb.append("\"));");
    } else if (matchesAny(param1, "verifyTextNotPresent", "verifyTextPresent")) {

      if (param1.equals("verifyTextNotPresent")) {
        sb.append("verifyFalse");
      } else if (param1.equals("verifyTextPresent")) {
        sb.append("verifyTrue");
      }

      sb.append("(selenium.isTextPresent(\"");
      sb.append(param2);
      sb.append("\"));");
    } else if (matchesAny(param1, "verifyTitle")) {
      sb.append("verifyEquals(\"");
      sb.append(param2);
      sb.append("\", selenium.getTitle());");
    } else if (matchesAny(param1, "waitForElementNotPresent", "waitForElementPresent",
                          "waitForNotPartialText", "waitForNotTable", "waitForNotText", "waitForNotValue",
                          "waitForNotVisible", "waitForPartialText", "waitForTable", "waitForText",
                          "waitForTextNotPresent", "waitForTextPresent", "waitForValue", "waitForVisible")) {
      sb.append("for (int second = 0;; second++) {");
      sb.append("if (second >= 60) {");
      sb.append("fail(\"timeout\");");
      sb.append("}");

      sb.append("try {");
      sb.append("if (");

      if (matchesAny(param1, "waitForElementNotPresent", "waitForNotPartialText", "waitForNotTable", "waitForNotText", "waitForNotValue", "waitForNotVisible", "waitForTextNotPresent")) {
        sb.append("!");
      }

      if (matchesAny(param1, "waitForElementNotPresent", "waitForElementPresent")) {
        sb.append("selenium.isElementPresent");
        sb.append("(\"");
        sb.append(param2);
        sb.append("\")");
      } else if (matchesAny(param1, "waitForNotPartialText", "waitForPartialText")) {
        sb.append("selenium.isPartialText(\"");
        sb.append(param2);
        sb.append("\", ");

        if (param3.startsWith("${")) {
          sb.append("" + GETVALUE_REF + "(\"");

          String text = param3.substring(2, param3.length() - 1);

          sb.append(text);
          sb.append("\")");
        } else {
          sb.append("\"");
          sb.append(param3);
          sb.append("\"");
        }

        sb.append(")");
      } else if (matchesAny(param1, "waitForNotTable", "waitForTable")) {

        sb.append("StringPool.BLANK.equals(selenium.getTable(\"");
        sb.append(param2);
        sb.append("\"))");
      } else if (matchesAny("waitForNotText", "waitForText")) {
        sb.append("" + EVALUATE_REF + "(\"");
        sb.append(param3);
        sb.append("\").equals(selenium.getText(\"");
        sb.append(param2);
        sb.append("\"))");
      } else if (matchesAny(param1, "waitForNotValue", "waitForValue")) {

        sb.append("" + EVALUATE_REF + "(\"");
        sb.append(param3);
        sb.append("\").equals(selenium.getValue(\"");
        sb.append(param2);
        sb.append("\"))");
      } else if (matchesAny(param1, "waitForNotVisible", "waitForVisible")) {
        sb.append("selenium.isVisible");
        sb.append("(\"");
        sb.append(param2);
        sb.append("\")");
      } else if (matchesAny("waitForTextNotPresent", "waitForTextPresent")) {
        sb.append("selenium.isTextPresent");
        sb.append("(\"");
        sb.append(param2);
        sb.append("\")");
      }

      sb.append(") {");
      sb.append("break;");
      sb.append("}");
      sb.append("}");
      sb.append("catch (Exception e) {");
      sb.append("}");

      sb.append("Thread.sleep(1000);");
      sb.append("}");
    } else {
      LOGGER.warn("{} wasn't translated!");
    }
  }
}
