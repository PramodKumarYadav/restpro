
import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.powertester.config.TestEnvFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestSandbox {
    @RepeatedTest(10)
    void assertThatWeCanGetUserConfig() {
        final Config CONFIG = TestEnvFactory.getInstance().getConfig();
        log.info(TestEnvFactory.getInstance().getConfig().getString("TEST_ENV"));
        log.info(CONFIG.getString("CREATE_EMPLOYEE_ENDPOINT"));
        log.info(CONFIG.getString("ADMIN_NAME"));

        Boolean flag = CONFIG.getBoolean("TOGGLE");
        log.info(String.valueOf(flag));

        log.info(String.valueOf(CONFIG.getInt("NR_OF_USERS")));
        log.info(String.valueOf(CONFIG.getDouble("PRICE")));
        log.info(CONFIG.getString("USER_NAME"));
    }

    /**
     * a very basic test
     */
    @Test
    void assertThatTrueIsTrue() {
        assertTrue(true, "true is true");
    }

    @Tag("failing")
    @Test
    void assertThatADayIsADay() {
        assertEquals("day", "night", "true is true");
    }

    @Tag("flaky")
    @Test
    void createAFlakyTestCase() {
        long currentTimeStamp = System.currentTimeMillis();
        log.debug("currentTimeStamp: {}", currentTimeStamp);
        if (currentTimeStamp % 2 == 0) {
            assertTrue(true, "time is even");
        } else {
            assertTrue(false, "time is odd");
        }
    }

    @Test
    void testLogLevels(){
        log.info("this is info statement");
        log.debug("this is debug statement");
        log.warn("this is warning statement");
        log.error("this is error statement");
    }
}
