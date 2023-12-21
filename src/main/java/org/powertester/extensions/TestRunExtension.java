package org.powertester.extensions;

import static org.powertester.utils.DateUtils.getDateAsString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.powertester.database.DBConnection;
import org.slf4j.MDC;

@Slf4j
public class TestRunExtension
    implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

  private final AtomicBoolean runExecuted = new AtomicBoolean(false);
  private static final Path TEST_REPORT_PATH =
      Paths.get(".", "test-reports", getDateAsString("yyyy-MM-dd/HH-mm"));
  private long testRunStartTime;

  @Override
  public void beforeAll(final ExtensionContext extensionContext) {
    try {
      // Execute the method logic only if it hasn't been executed before
      if (!runExecuted.get() && runExecuted.compareAndSet(false, true)) {
        MDC.put("testContext", "Onetime TestRun Setup");
        log.info("Test run started.");

        testRunStartTime = System.currentTimeMillis();

        Locale.setDefault(Locale.ENGLISH);

        // Create a new instance of DBConnection so that it can be used for the entire test run.
        DBConnection.getInstance();

        Files.createDirectories(TEST_REPORT_PATH);

        // The following line registers a callback hook to be executed when the root test context is
        // shut down.
        extensionContext
            .getRoot()
            .getStore(ExtensionContext.Namespace.GLOBAL)
            .put("TestRunExtension", this);
      }
    } catch (Exception e) {
      log.error("Error initializing TestRunExtension", e);
      log.error("⚠ Cancelling test run since tests depend on TestRunExtension");
      System.exit(1);
    } finally {
        MDC.clear();
    }
  }

  @Override
  public void close() {
    MDC.put("testContext", "TestRun Completed");
    DBConnection.getInstance().closeConnectionPool();

    log.info(
        "Test run completed in {} seconds.",
        (System.currentTimeMillis() - testRunStartTime) / 1000.0);
    MDC.clear();
  }
}
