package org.powertester.extensions.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.typesafe.config.Config;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.powertester.config.TestConfig;
import org.powertester.extensions.TimingExtension;

@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestRunMetaData {
  private static final String PROJECT = "restpro";
  private static final Config CONFIG = TestConfig.getInstance().getConfig();

  private static final String RUN_TIME = LocalDateTime.now(ZoneId.of("UTC")).toString();

  private static final String RUN_NAME = getRunName();
  private static final String TRIGGERED_BY = getTriggeredBy();

  /**
   * Note: Jackson would ignore all above static variables when creating a JSON object to push to
   * Elastic; and will only consider below "fields" to create a Json data to publish.
   */
  private String project;

  @JsonProperty("run-time")
  private String runTime;

  @JsonProperty("run-name")
  private String runName;

  @JsonProperty("test-class")
  private String testClass;

  @JsonProperty("test-name")
  private String testName;

  private String status;
  private String reason;

  @JsonProperty("triggered-by")
  private String triggeredBy;

  @JsonProperty("time (Sec)")
  private String duration;

  public TestRunMetaData setBody(ExtensionContext context) {
    project = PROJECT;

    runTime = RUN_TIME;
    runName = RUN_NAME;

    testClass = context.getTestClass().orElseThrow().getSimpleName();
    testName = context.getDisplayName();

    setDuration();

    setTestStatusAndReason(context);

    triggeredBy = TRIGGERED_BY;

    return this;
  }

  private void setDuration() {
    if (TimingExtension.getTestExecutionTimeThread() >= 5) {
      duration = TimingExtension.getTestExecutionTimeThread() + " ⏰";
    } else {
      duration = String.valueOf(TimingExtension.getTestExecutionTimeThread());
    }

    log.info("duration {}", duration);
  }

  private void setTestStatusAndReason(ExtensionContext context) {
    boolean testStatus = context.getExecutionException().isPresent();
    if (testStatus) {
      status = "❌";
      reason = "🐞 " + context.getExecutionException().toString();
    } else {
      status = "✅";
      reason = "🌻";
    }
  }

  private static String getTriggeredBy() {
    if (CONFIG.getString("TRIGGERED_BY").isEmpty()) {
      return System.getProperty("user.name");
    } else {
      return CONFIG.getString("TRIGGERED_BY");
    }
  }

  private static String getRunName() {
    if (CONFIG.getString("RUN_NAME").isEmpty()) {
      return new Faker().funnyName().name();
    } else {
      return CONFIG.getString("RUN_NAME");
    }
  }
}
