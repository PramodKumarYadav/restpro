package org.powertester.extensions;

import static org.powertester.utils.DateUtils.getDateAsString;

import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.powertester.database.DBConnection;
import org.powertester.utils.FileUtils;

@Slf4j
public class TestRunExtension
    implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
  private static final Lock LOCK = new ReentrantLock();

  private static volatile boolean started = false;

  private static final String TEST_REPORT_PATH =
      "./test-reports/" + getDateAsString("yyyy-MM-dd/HH-mm") + "/";

  public static String getTestReportPath() {
    return TEST_REPORT_PATH;
  }

  private long testRunStartTime;

  @Override
  public void beforeAll(final ExtensionContext extensionContext) throws Exception {
    LOCK.lock();
    try {
      if (!started) {
        started = true;
        log.info("Test run started.");

        testRunStartTime = System.currentTimeMillis();

        Locale.setDefault(Locale.ENGLISH);

        // Create a new instance of DBConnection so that it can be used for the entire test run.
        DBConnection.getInstance();

        FileUtils.createDirectory(TEST_REPORT_PATH);

        // The following line registers a callback hook to be executed when the root test context is
        // shut down.
        extensionContext
            .getRoot()
            .getStore(ExtensionContext.Namespace.GLOBAL)
            .put("TestRunExtension", this);
      }
    } finally {
      LOCK.unlock();
    }
  }

  @Override
  public void close() throws Throwable {
    DBConnection.getInstance().closeConnectionPool();

    log.info(
        "Test run completed in {} seconds.",
        (System.currentTimeMillis() - testRunStartTime) / 1000.0);
  }
}
