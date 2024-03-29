package healthcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.powertester.annotations.HealthCheckTest;
import org.powertester.healthcheck.HealthCheckAPI;

@Slf4j
@HealthCheckTest
class HealthCheckTests {
  @Test
  void assertThatRestfulBookerApplicationIsUpAndHealthy() {
    // Act
    Response response = HealthCheckAPI.healthCheck();
    assertEquals(201, response.getStatusCode());
  }
}
