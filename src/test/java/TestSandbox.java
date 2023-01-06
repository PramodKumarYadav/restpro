
import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.powertester.annotations.FailingTest;
import org.powertester.annotations.FlakyTest;
import org.powertester.annotations.SmokeTest;
import org.powertester.config.TestEnvFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestSandbox {
    @RepeatedTest(10)
    void assertThatWeCanGetUserConfig() {
        final Config CONFIG = TestEnvFactory.getInstance().getConfig();
        final String expectedEnv = CONFIG.getString("TEST_ENV");
        assertAll("Config test",
                () -> assertEquals(expectedEnv, CONFIG.getString("TEST_ENV"), "TEST_ENV"),
                () -> assertEquals("/employee/create", CONFIG.getString("CREATE_EMPLOYEE_ENDPOINT"), "CREATE_EMPLOYEE_ENDPOINT"),
                () -> assertEquals(expectedEnv.toLowerCase()+ "-admin-user", CONFIG.getString("ADMIN_NAME"), "ADMIN_NAME"),
                () -> assertFalse(CONFIG.getBoolean("TOGGLE"), "TOGGLE"),
                () -> assertEquals(10, CONFIG.getInt("NR_OF_USERS"), "NR_OF_USERS"),
                () -> assertEquals(123.456, CONFIG.getDouble("PRICE"), "PRICE"),
                () -> assertEquals(expectedEnv.toLowerCase() + "-user", CONFIG.getString("USER_NAME"), "USER_NAME")
        );
    }

    /**
     * a very basic test
     */
    @SmokeTest
    void assertThatTrueIsTrue() {
        assertTrue(true, "true is true");
    }

    @FailingTest
    void assertThatADayIsADay() {
        assertEquals("day", "night", "true is true");
    }

    @FlakyTest
    void createAFlakyTestCase() {
        long currentTimeStamp = System.currentTimeMillis();
        log.debug("currentTimeStamp: {}", currentTimeStamp);
        if (currentTimeStamp % 2 == 0) {
            assertTrue(true, "time is even");
        } else {
            assertTrue(false, "time is odd");
        }
    }
}

