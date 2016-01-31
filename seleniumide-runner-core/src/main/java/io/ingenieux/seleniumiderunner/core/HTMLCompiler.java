package io.ingenieux.seleniumiderunner.core;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.Validate;
import org.codehaus.janino.ClassBodyEvaluator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.Iterator;

import static java.lang.String.format;

public class HTMLCompiler {
  private static final Logger LOGGER = LoggerFactory.getLogger(HTMLCompiler.class);

  final String xmlSource;

  public HTMLCompiler(String xmlSource) {
    this.xmlSource = xmlSource;
  }

  public HTMLCompiler parse() throws Exception {
    SAXReader saxReader = new SAXReader();

    LOGGER.debug("Parsing Source: {}", xmlSource);

    saxReader.setValidation(false);
    saxReader.setEntityResolver(null);
    saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

    this.document = saxReader.read(new StringReader(xmlSource));

    return this;
  }

  Document document;

  DefaultElement rootElement = null;

  public String generateSource() {
    StringBuilder sb = new StringBuilder();

    document.getRootElement().accept(new VisitorSupport() {
      @Override
      public void visit(Element node) {
        if ("tbody".equals(node.getName())) {
          rootElement = (DefaultElement) node;
        }
      }
    });

    Validate.notNull(rootElement, "tbody not found.");

    StatementGenerator converter = new StatementGenerator();

    for (Iterator<?> itElement = rootElement.elementIterator("tr"); itElement.hasNext(); ) {
      Element childElement = (Element) itElement.next();

      int i = 0;
      String[] args = new String[3];
      for (Iterator<?> itArgumentElement = childElement
          .elementIterator("td"); itArgumentElement.hasNext(); ) {
        Element argElement = (Element) itArgumentElement.next();

        args[i++] = argElement.getTextTrim();
      }

      LOGGER.debug("Statement: {} {} {}", args[0], args[1], args[2]);

      sb.append(format("        LOGGER.info(\"running: '{} {} {}'\", \"%s\", \"%s\", \"%s\");\n",
                       StringEscapeUtils.escapeJava(args[0]),
                       StringEscapeUtils.escapeJava(args[1]),
                       StringEscapeUtils.escapeJava(args[2])));

      sb.append("        ");

      int nIndex = sb.length();

      converter.translate(args[0], args[1], args[2], sb);

      int lastIndex = sb.length();

      LOGGER.debug("Generated Statement: {}", sb.subSequence(nIndex, lastIndex));

      sb.append("\n");

    }

    final String generatedSource = sb.toString();

    LOGGER.info("Generated Source: {}", generatedSource);

    return generatedSource;
  }

  public <T> T getInstance(Class<T> clazz) throws Exception {
    String source = generateSource();

    final String formattedSource = format("public void execute() throws Exception { \n %s\n}\n", source);

    LOGGER.debug("Formatted Source: {}", formattedSource);

    ClassBodyEvaluator classBodyEvaluator = new ClassBodyEvaluator();

    classBodyEvaluator.setExtendedClass(clazz);

    classBodyEvaluator.cook(formattedSource);

    Class<T> resultingClazz = classBodyEvaluator.getClazz();

    return resultingClazz.newInstance();
  }

}
