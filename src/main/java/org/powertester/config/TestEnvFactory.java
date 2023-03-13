package org.powertester.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

/**
 * Env configuration once loaded, is to remain constant for all classes using it. Thus we will
 * follow Singleton design pattern here. For future reference on this topic:
 * https://github.com/lightbend/config
 */
@Slf4j
public class TestEnvFactory {
  /**
   * With this approach, we are relying on JVM to create the unique instance of TestEnvFactory when
   * the class is loaded. The JVM guarantees that the instance will be created before any thread
   * accesses the static uniqueInstance variable. This code is thus guaranteed to be thread safe.
   */
  private static final TestEnvFactory UNIQUE_INSTANCE = new TestEnvFactory();

  private Config config;

  private TestEnvFactory() {
    config = setConfig();
  }

  public static TestEnvFactory getInstance() {
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

    TestEnv testEnv = config.getEnum(TestEnv.class, "TEST_ENV");
    return getAllConfigFromFilesInTheResourcePath(testEnv.getValue());
  }

  private Config getAllConfigFromFilesInTheResourcePath(String resourceBasePath) {
    try {
      String path = String.format("src/main/resources/%s", resourceBasePath);
      log.info("path: {}", path);

      File testEnvDir = new File(path);
      for (File file : Objects.requireNonNull(testEnvDir.listFiles())) {
        String resourceFileBasePath = String.format("%s/%s", resourceBasePath, file.getName());
        log.info("resourceFileBasePath: {}", resourceFileBasePath);

        Config childConfig = ConfigFactory.load(resourceFileBasePath);
        config = config.withFallback(childConfig);
      }

      return config;
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new IllegalStateException("Could not parse config");
    }
  }
}
