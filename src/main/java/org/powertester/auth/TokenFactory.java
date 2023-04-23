package org.powertester.auth;

import static io.restassured.RestAssured.given;

import com.typesafe.config.Config;
import io.restassured.response.Response;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.powertester.config.TestConfig;

@Slf4j
public class TokenFactory {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();

  private final String maintainerToken;
  private final String adminToken;

  private static final TokenFactory UNIQUE_INSTANCE = new TokenFactory();

  private TokenFactory() {
    AuthBody authBodyMaintainer =
        AuthBody.builder()
            .setUsername(CONFIG.getString("MAINTAINER_USERNAME"))
            .setPassword(CONFIG.getString("MAINTAINER_PASSWORD"))
            .build();
    this.maintainerToken = getToken(authBodyMaintainer);

    AuthBody authBodyAdmin =
        AuthBody.builder()
            .setUsername(CONFIG.getString("ADMIN_USERNAME"))
            .setPassword(CONFIG.getString("ADMIN_PASSWORD"))
            .build();
    this.adminToken = getToken(authBodyAdmin);
  }

  public static TokenFactory getInstance() {
    return UNIQUE_INSTANCE;
  }

  public String getTokenFor(Scope scope) {
    switch (scope) {
      case GUEST:
        return "";
      case MAINTAINER:
        return maintainerToken;
      case ADMIN:
        return adminToken;
      default:
        throw new IllegalStateException(
            "Not a valid scope. Pick a scope from " + Arrays.toString(Scope.values()));
    }
  }

  private String getToken(AuthBody authBody) {
    Response response =
        given()
            .header("Content-Type", "application/json")
            .baseUri(CONFIG.getString("BASE_URL"))
            .body(authBody)
            .log()
            .all()
            .when()
            .post(CONFIG.getString("AUTH_ENDPOINT"))
            .then()
            .log()
            .all()
            .extract()
            .response();

    log.info("token: {}", response.body().jsonPath().getString("token"));
    return response.body().jsonPath().getString("token");
  }
}
