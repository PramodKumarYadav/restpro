package org.powertester.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

/**
 * Env configuration once loaded, is to remain constant for all classes using it. Thus we will
 * follow Singleton design pattern here. For future reference on this topic:
 * https://github.com/lightbend/config
 */
@Slf4j
public class TestConfig {
  /**
   * With this approach, we are relying on JVM to create the unique instance of TestEnvFactory when
   * the class is loaded. The JVM guarantees that the instance will be created before any thread
   * accesses the static uniqueInstance variable. This code is thus guaranteed to be thread safe.
   */
  private static final TestConfig UNIQUE_INSTANCE = new TestConfig();

  private Config config;

  private TestConfig() {
    config = setConfig();
  }

  public static TestConfig getInstance() {
    return UNIQUE_INSTANCE;
  }

  public Config getConfig() {
    return config;
  }

  private Config setConfig() {
    log.info("Call setConfig only once for the whole test run!");

    // Standard config load behavior (loads common config from application.conf file)
    // https://github.com/lightbend/config#standard-behavior
    config = ConfigFactory.load();

    Config choicesConfig = ConfigFactory.load("choices");
    config = config.withFallback(choicesConfig);

    config = getAllConfigFromFilesInTheResourcePath("common");

    TestEnv testEnv = TestEnv.getEnumByValue(config.getString("TEST_ENV"));
    return getAllConfigFromFilesInTheResourcePath(testEnv.getValue());
  }

  private Config getAllConfigFromFilesInTheResourcePath(String resourceBasePath) {
    try {
      for (File file :
          Objects.requireNonNull(
              Paths.get("src/main/resources/" + resourceBasePath).toFile().listFiles())) {
        log.info("file path: {}", file);

        Config childConfig =
            ConfigFactory.load(String.format("%s/%s", resourceBasePath, file.getName()));
        config = config.withFallback(childConfig);
      }

      return config;
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new IllegalStateException("Could not parse config. Got issues in parsing File path.");
    }
  }
}
