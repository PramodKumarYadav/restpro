package org.powertester.healthcheck;

import static io.restassured.RestAssured.given;
import static org.powertester.auth.Scope.GUEST;

import com.typesafe.config.Config;
import io.restassured.response.Response;
import org.powertester.basespec.SpecFactory;
import org.powertester.config.TestConfig;

public class HealthCheckAPI {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();

  public static Response healthCheck() {
    // Cookie: token={{auth_token}}
    return given()
        .spec(SpecFactory.getSpecFor(GUEST).build())
        .log()
        .ifValidationFails()
        .when()
        .get(CONFIG.getString("HEALTHCHECK_ENDPOINT"))
        .then()
        .log()
        .ifError()
        .extract()
        .response();
  }
}
