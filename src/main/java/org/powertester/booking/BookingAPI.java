package org.powertester.booking;

import static io.restassured.RestAssured.given;

import com.typesafe.config.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.powertester.auth.Scope;
import org.powertester.basespec.SpecFactory;
import org.powertester.config.TestConfig;

public class BookingAPI {
  private static final Config CONFIG = TestConfig.getInstance().getConfig();
  private Scope scope;

  private BookingAPI(Scope scope) {
    this.scope = scope;
  }

  public static BookingAPI useAs(Scope scope) {
    return new BookingAPI(scope);
  }

  public Response newBooking(Booking booking) {
    return RestAssured.given()
        .spec(SpecFactory.getSpecFor(scope).build())
        .body(booking)
        .log()
        .ifValidationFails()
        .when()
        .post(CONFIG.getString("BOOKING_ENDPOINT"))
        .then()
        .log()
        .ifError()
        .extract()
        .response();
  }

  public Response getBooking(Long bookingId) {
    return RestAssured.given()
        .spec(SpecFactory.getSpecFor(scope).build())
        .log()
        .ifValidationFails()
        .when()
        .get(CONFIG.getString("BOOKING_ID_ENDPOINT"), bookingId)
        .then()
        .log()
        .ifError()
        .extract()
        .response();
  }

  public Response updateBooking(Booking booking, Long bookingId) {
    // Cookie: token={{auth_token}}
    return given()
        .spec(SpecFactory.getSpecFor(scope).build())
        .body(booking)
        .log()
        .ifValidationFails()
        .when()
        .put(CONFIG.getString("BOOKING_ID_ENDPOINT"), bookingId)
        .then()
        .log()
        .ifError()
        .extract()
        .response();
  }

  public Response patchBooking(Booking booking, Long bookingId) {
    // Cookie: token={{auth_token}}
    return given()
        .spec(SpecFactory.getSpecFor(scope).build())
        .body(booking)
        .log()
        .ifValidationFails()
        .when()
        .patch(CONFIG.getString("BOOKING_ID_ENDPOINT"), bookingId)
        .then()
        .log()
        .ifError()
        .extract()
        .response();
  }

  public Response deleteBooking(Long bookingId) {
    // Cookie: token={{auth_token}}
    return given()
        .spec(SpecFactory.getSpecFor(scope).build())
        .log()
        .ifValidationFails()
        .when()
        .delete(CONFIG.getString("BOOKING_ID_ENDPOINT"), bookingId)
        .then()
        .log()
        .ifError()
        .extract()
        .response();
  }
}
