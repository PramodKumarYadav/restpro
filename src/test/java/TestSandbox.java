
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestSandbox {
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
