package org.powertester.booking;

import com.typesafe.config.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.powertester.BaseSpec.BaseSpec;
import org.powertester.authtoken.AuthAPI;
import org.powertester.config.TestConfig;

import static io.restassured.RestAssured.given;

public class BookingAPI {
    private static final Config CONFIG = TestConfig.getInstance().getConfig();

    public static Response newBooking(BookingBody bookingBody) {
        return RestAssured.given()
                .spec(BaseSpec.get().build())
                .body(bookingBody)
                .log()
                .all()
                .when()
                .post(CONFIG.getString("BOOKING_ENDPOINT"))
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    // todo: This method is tied to a specific auth token (user type/scope type).
    //  for your test to be flexible, your methods should be auth agnostic. So
    //  refactor this method or class later.
    public static Response updateBooking(BookingBody bookingBody, String bookingId) {
        // Cookie: token={{auth_token}}
        return given()
                .spec(BaseSpec.get(AuthAPI.getToken()).build())
                .body(bookingBody)
                .log()
                .all()
                .when()
                .put(CONFIG.getString("BOOKING_ID_ENDPOINT"), bookingId)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    // todo: This method is tied to a specific auth token (user type/scope type).
    //  for your test to be flexible, your methods should be auth agnostic. So
    //  refactor this method or class later.
    public static Response patchBooking(BookingBody bookingBody, String bookingId) {
        // Cookie: token={{auth_token}}
        return given()
                .spec(BaseSpec.get(AuthAPI.getToken()).build())
                .body(bookingBody)
                .log()
                .all()
                .when()
                .patch(CONFIG.getString("BOOKING_ID_ENDPOINT"), bookingId)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    // todo: This method is tied to a specific auth token (user type/scope type).
    //  for your test to be flexible, your methods should be auth agnostic. So
    //  refactor this method or class later.
    public static Response deleteBooking(String bookingId) {
        // Cookie: token={{auth_token}}
        return given()
                .spec(BaseSpec.get(AuthAPI.getToken()).build())
                .log()
                .all()
                .when()
                .delete(CONFIG.getString("BOOKING_ID_ENDPOINT"), bookingId)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }
}
