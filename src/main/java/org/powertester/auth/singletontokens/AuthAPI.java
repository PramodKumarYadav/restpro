package org.powertester.auth.singletontokens;

import static io.restassured.RestAssured.given;

import com.typesafe.config.Config;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.powertester.auth.AuthBody;
import org.powertester.config.TestConfig;

@Slf4j
public class AuthAPI {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();

  public static String getToken(AuthBody authBody) {
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

    log.info("singleton-token: {}", response.body().jsonPath().getString("token"));
    return response.body().jsonPath().getString("token");
  }
}
