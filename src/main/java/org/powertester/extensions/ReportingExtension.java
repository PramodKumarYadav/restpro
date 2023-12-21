package org.powertester.extensions;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.powertester.config.TestConfig;
import org.powertester.extensions.report.PublishResults;
import org.powertester.extensions.report.TestRunMetaData;

@Slf4j
public class ReportingExtension implements AfterTestExecutionCallback {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();
  private static final Boolean PUBLISH_RESULTS_TO_ELASTIC =
      CONFIG.getBoolean("PUBLISH_RESULTS_TO_ELASTIC");

  @Override
  public void afterTestExecution(ExtensionContext context) throws Exception {
    if (PUBLISH_RESULTS_TO_ELASTIC) {
      log.info("publishing results to elastic");
      TestRunMetaData testRunMetaData = new TestRunMetaData().setBody(context);
      PublishResults.toElastic(testRunMetaData);
    } else {
      log.info("PUBLISH_RESULTS_TO_ELASTIC flag is set to false");
    }
  }
}
