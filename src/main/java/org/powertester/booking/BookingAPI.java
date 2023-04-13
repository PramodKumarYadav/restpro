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

    public static Response newBooking(Booking booking) {
        return RestAssured.given()
                .spec(BaseSpec.get().build())
                .body(booking)
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

    public static Response getBooking(Long bookingId) {
        return RestAssured.given()
                .spec(BaseSpec.get().build())
                .log()
                .all()
                .when()
                .get(CONFIG.getString("BOOKING_ID_ENDPOINT"), bookingId)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    // todo: This method is tied to a specific auth token (user type/scope type).
    //  for your test to be flexible, your methods should be auth agnostic. So
    //  refactor this method or class later.
    public static Response updateBooking(Booking booking, Long bookingId) {
        // Cookie: token={{auth_token}}
        return given()
                .spec(BaseSpec.get(AuthAPI.getToken()).build())
                .body(booking)
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
    public static Response patchBooking(Booking booking, Long bookingId) {
        // Cookie: token={{auth_token}}
        return given()
                .spec(BaseSpec.get(AuthAPI.getToken()).build())
                .body(booking)
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
    public static Response deleteBooking(Long bookingId) {
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
