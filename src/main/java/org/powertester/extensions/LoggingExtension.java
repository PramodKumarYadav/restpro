package org.powertester.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.MDC;

public class LoggingExtension implements BeforeEachCallback, AfterEachCallback {

  @Override
  public void beforeEach(ExtensionContext context) {
    MDC.put("testContext", context.getDisplayName().replace("()", ""));
  }

  @Override
  public void afterEach(ExtensionContext context) {
    MDC.clear();
  }
}
