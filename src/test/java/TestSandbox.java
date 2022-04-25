
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        // Todo: Remove this line with a logging statement
        System.out.println(currentTimeStamp);
        if (currentTimeStamp % 2 == 0) {
            assertTrue(true, "time is even");
        } else {
            assertTrue(false, "time is odd");
        }
    }
}
