package org.powertester.config;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class TestEnvFactory {
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
        try{
            log.info("setConfig called");
            // Standard config load behavior: https://github.com/lightbend/config#standard-behavior
            config = ConfigFactory.load();

            TestEnv testEnv = config.getEnum(TestEnv.class, "TEST_ENV");

            /**
             * In windows both uppercase and lower case directories are treated the same. However not in linux.
             * So if we do not convert the repo to lower case, it would work on windows but not in CI- on linux container.
             */
            String testEnvName = testEnv.toString().toLowerCase();
            String path = String.format("src/main/resources/%s", testEnvName);
            log.error("path: {}", path);

            File testEnvDir = new File(String.valueOf(path));
            for (File file : testEnvDir.listFiles()) {
                String resourceBaseName = String.format("%s/%s", testEnvName, file.getName());
                log.error("resourceBaseName: {}", resourceBaseName);

                Config childConfig = ConfigFactory.load(resourceBaseName);
                config = config.withFallback(childConfig);
            }

            return config;
        }catch(Exception exception){
            exception.printStackTrace();
            throw new IllegalStateException("Could not parse config");
        }
    }
}
