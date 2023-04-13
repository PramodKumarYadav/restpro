import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * These tests are for demo purpose only (and to show the challenges that comes with relying only on RestAssured
 * for writing tests. Do not use this as reference to design your tests.
 */

public class DoNotUseRestAssuredOnlyTests {
    @Test
    // Given_When_Then
    public void assertThatUserCanCreateABooking() {
        String jsonBody = "{\n" +
                "  \"firstname\": \"Jim\",\n" +
                "  \"lastname\": \"Brown\",\n" +
                "  \"totalprice\": 111,\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2018-01-01\",\n" +
                "    \"checkout\": \"2019-01-01\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Breakfast\"\n" +
                "}";

        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .baseUri("https://restful-booker.herokuapp.com")
                .body(jsonBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .body("booking.firstname", equalTo("Jim"),
                        "booking.lastname", equalTo("Brown"),
                        "booking.totalprice", equalTo(111),
                        "booking.depositpaid", equalTo(true),
                        "booking.bookingdates.checkin", equalTo("2018-01-01"),
                        "booking.bookingdates.checkout", equalTo("2019-01-01"),
                        "booking.additionalneeds", equalTo("Breakfast")
                )
                .extract()
                .response()
                .prettyPrint();

    }

    @Test
    // Given_When_Then
    public void assertThatUserCanCreateABookingWithOnlyMandatoryDetails() {
        String jsonBody = "{\n" +
                "  \"firstname\": \"Jim\",\n" +
                "  \"lastname\": \"Brown\",\n" +
                "  \"totalprice\": 111,\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2018-01-01\",\n" +
                "    \"checkout\": \"2019-01-01\"\n" +
                "  }\n" +
                "}";

        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .baseUri("https://restful-booker.herokuapp.com")
                .body(jsonBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .body("booking.firstname", equalTo("Jim"),
                        "booking.lastname", equalTo("Brown"),
                        "booking.totalprice", equalTo(111),
                        "booking.depositpaid", equalTo(true),
                        "booking.bookingdates.checkin", equalTo("2018-01-01"),
                        "booking.bookingdates.checkout", equalTo("2019-01-01")
                )
                .extract()
                .response()
                .prettyPrint();

    }

    @Test
    // Given_When_Then
    public void assertThatUserCanNotCreateABookingWithMissingMandatoryDetails() {
        String jsonBody = "{\n" +
                "  \"firstname\": \"Jim\",\n" +
                "  \"lastname\": \"Brown\",\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2018-01-01\",\n" +
                "    \"checkout\": \"2019-01-01\"\n" +
                "  }\n" +
                "}";

        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .baseUri("https://restful-booker.herokuapp.com")
                .body(jsonBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(500)
                .extract()
                .response()
                .prettyPrint();

    }
}
