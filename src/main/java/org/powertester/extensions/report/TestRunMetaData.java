package org.powertester.extensions.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.powertester.extensions.TimingExtension;

import java.time.LocalDateTime;

@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestRunMetaData {
    private static final String PROJECT = "zero";
    private static final String TEST_RUN_TIME = LocalDateTime.now().toString();
    private static final String USER_NAME = System.getProperty("user.name");

    /**
     * Note: Jackson would ignore all above static variables when creating a JSON object to push to Elastic;
     * and will only consider below "fields" to create a Json data to publish.
     * */
    private String project;

    @JsonProperty("test-run")
    private String testRun;

    @JsonProperty("test-class")
    private String testClass;

    @JsonProperty("test-name")
    private String testName;

    private String status;
    private String reason;

    @JsonProperty("triggered-by")
    private String triggeredBy;

    @JsonProperty("time (Sec)")
    private String duration;

    public TestRunMetaData setBody(ExtensionContext context) {
        project = PROJECT;
        testRun = TEST_RUN_TIME;

        testClass = context.getTestClass().orElseThrow().getSimpleName();
        testName = context.getDisplayName();

        setDuration();

        setTestStatusAndReason(context);

        triggeredBy = USER_NAME;

        return this;
    }

    private void setDuration() {
        if(TimingExtension.getTestExecutionTimeThread() >= 5){
            duration =  TimingExtension.getTestExecutionTimeThread() + " ⏰";
        }else{
            duration = String.valueOf(TimingExtension.getTestExecutionTimeThread());
        }

        log.info("duration {}}️" , duration);
    }

    private void setTestStatusAndReason(ExtensionContext context) {
        boolean testStatus = context.getExecutionException().isPresent();
        if (testStatus) {
            status = "❌";
            reason =  "🐞 " + context.getExecutionException().toString();
        } else {
            status = "✅";
            reason = "🌻";
        }
    }
}
