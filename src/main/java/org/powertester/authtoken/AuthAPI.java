package org.powertester.authtoken;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;

@Slf4j
public class AuthAPI {
    // todo: create a singleton instance of auth tokens.
    public static String getToken() {
        AuthBody authBody = AuthBody.getInstance();

        Response response = given()
                .header("Content-Type", "application/json")
                .baseUri("https://restful-booker.herokuapp.com")
                .body(authBody)
                .log()
                .all()
                .when()
                .post("/auth")
                .then()
                .log()
                .all()
                .extract()
                .response();

        log.info("token: {}", response.body().jsonPath().getString("token"));
        return response.body().jsonPath().getString("token");
    }
}
