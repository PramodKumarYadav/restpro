package org.powertester.booking;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.powertester.BaseSpec.BaseSpec;
import org.powertester.authtoken.AuthAPI;

import static io.restassured.RestAssured.given;

public class BookingAPI {
    public static Response newBooking(BookingBody bookingBody) {
        return RestAssured.given()
                .spec(BaseSpec.get().build())
                .body(bookingBody)
                .log()
                .all()
                .when()
                .post("/booking")
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
                .put("/booking/" + bookingId)
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
                .patch("/booking/" + bookingId)
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
                .delete("/booking/" + bookingId)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }
}
