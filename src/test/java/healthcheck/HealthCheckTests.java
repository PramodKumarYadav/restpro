package healthcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.powertester.annotations.HealthCheckTest;
import org.powertester.healthcheck.HealthCheckAPI;
import setup.TestSetup;

@Slf4j
public class HealthCheckTests extends TestSetup {
  @HealthCheckTest
  void assertThatRestfulBookerApplicationIsUpAndHealthy() {
    // Act
    Response response = HealthCheckAPI.healthCheck();
    assertEquals(200, response.getStatusCode());
  }
}
