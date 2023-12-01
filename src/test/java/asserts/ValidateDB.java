package asserts;

import static org.junit.jupiter.api.Assertions.fail;

import com.typesafe.config.Config;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.awaitility.Awaitility;
import org.powertester.config.TestConfig;
import org.powertester.data.TestData;
import org.powertester.database.DBConnection;

@Slf4j
public class ValidateDB {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();
  private static final String AWAITILITY_TIMEOUT_IN_SECONDS =
      CONFIG.getString("AWAITILITY_TIMEOUT_IN_SECONDS");
  private static final String AWAITILITY_POLL_INTERVAL_IN_SECONDS =
      CONFIG.getString("AWAITILITY_POLL_INTERVAL_IN_SECONDS");
  private static final String AWAITILITY_POLL_DELAY_IN_SECONDS =
      CONFIG.getString("AWAITILITY_POLL_DELAY_IN_SECONDS");
  private List<Map<String, String>> resultList;
  private final String sqlStatement;
  private final String[] sqlParameters;
  private final TestData testData;
  private final SoftAssertions softly = new SoftAssertions();

  private ValidateDB(TestData testData, String sqlStatement, String... sqlParameters) {
    this.testData = testData;
    this.sqlStatement = sqlStatement;
    this.sqlParameters = sqlParameters;
  }

  public ValidateDB with(TestData testData, String sqlStatement, String... sqlParameters) {
    return new ValidateDB(testData, sqlStatement, sqlParameters);
  }

  public ValidateDB hasRecordCount(int expectedCount) {
    try {
      Awaitility.await()
          .atMost(Long.parseLong(AWAITILITY_TIMEOUT_IN_SECONDS), TimeUnit.SECONDS)
          .pollInterval(Long.parseLong(AWAITILITY_POLL_INTERVAL_IN_SECONDS), TimeUnit.SECONDS)
          .pollDelay(Long.parseLong(AWAITILITY_POLL_DELAY_IN_SECONDS), TimeUnit.SECONDS)
          .alias(testData.getValue("TEST_NAME"))
          .untilAsserted(
              () -> {
                resultList =
                    DBConnection.getInstance()
                        .executePreparedStatement(sqlStatement, sqlParameters);
                Assertions.assertThat(resultList)
                    .as(testData.getValue("TEST_NAME") + "has record count")
                    .hasSize(expectedCount);
              });
    } catch (Exception e) {
      fail(
          "Error executing SQL statement: "
              + sqlStatement
              + " with parameters: "
              + String.join(", ", sqlParameters),
          e);
    }

    return this;
  }

  public ValidateDB andActualValuesMatchExpectedValues() {
    log.info("Validating actual values from DB with expected values from test data");

    Map<String, String> actualValues = resultList.get(0);
    testData.getKeyTypeMap().entrySet().stream()
        .filter(entry -> entry.getValue().equalsIgnoreCase("output"))
        .forEach(
            entry -> {
              String key = entry.getKey();
              String expectedValue = testData.getKeyValueMap().get(key);
              String actualValue = actualValues.get(key);
              softly
                  .assertThat(actualValue)
                  .as(
                      testData.getValue("TEST_NAME")
                          + " with sqlParameters: "
                          + Arrays.toString(sqlParameters)
                          + ": key: "
                          + key)
                  .isEqualTo(expectedValue);
            });

    return this;
  }

  public void assertAll() {
    softly.assertAll();
  }
}
