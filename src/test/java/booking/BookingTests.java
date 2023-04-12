package booking;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.powertester.authtoken.AuthAPI;
import org.powertester.booking.BookingAPI;
import org.powertester.booking.BookingBody;
import setup.TestSetup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class BookingTests extends TestSetup {
    @Test
    void assertThatAUserCanCreateANewBooking() {
        // Arrange
        BookingBody bookingBody = BookingBody.getInstance();

        // Act
        Response response = BookingAPI.newBooking(bookingBody);

        // Assert
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void assertThatAUserCanCreateANewBookingByProvidingOnlyMandatoryFields() {
        // Arrange
        BookingBody bookingBody = BookingBody.getInstance();
        bookingBody.setAdditionalneeds(null);

        // Act
        Response response = BookingAPI.newBooking(bookingBody);

        // Assert
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void testToken() {
        AuthAPI.getToken();
    }

    // todo: refactor these tests to make them more readable and efficient.
    @Test
    void assertThatAUserCanCreateAndUpdateABooking() {
        // Arrange
        BookingBody bookingBody = BookingBody.getInstance();

        // Act
        Response response = BookingAPI.newBooking(bookingBody);
        String bookingId = response.body().jsonPath().getString("bookingid");

        // Assert
        assertEquals(200, response.getStatusCode());

        // Now update this booking
        // Arrange
        bookingBody.setFirstname("Vinod");

        Response responseUpdate = BookingAPI.updateBooking(bookingBody, bookingId);

        // Assert
        assertEquals(200, response.getStatusCode());

    }
}
