package org.powertester.extensions;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.*;

@Slf4j
public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static ThreadLocal<Double> testExecutionTimeThread = new ThreadLocal<>();
    private long startTimeMethod;

    public static Double getTestExecutionTimeThread() {
        return testExecutionTimeThread.get();
    }

    public static void removeTestExecutionTimeThread() {
        testExecutionTimeThread.remove();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        startTimeMethod = System.currentTimeMillis();
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        double duration = (System.currentTimeMillis() - startTimeMethod) / 1000.0;
        log.info("Test [{}] took {} seconds.", context.getRequiredTestMethod().getName(), duration);

        // Now set this variable as a thread local so that it can be used for reporting total execution time.
        testExecutionTimeThread.set(duration);
    }
}
