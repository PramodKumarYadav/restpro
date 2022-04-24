
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

    @Test
    void assertThatADayIsADay() {
        assertEquals("day", "day", "true is true");
    }
}
