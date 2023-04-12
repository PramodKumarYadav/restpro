package booking;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.powertester.booking.BookingAPI;
import org.powertester.booking.BookingBody;
import setup.TestSetup;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        bookingBody.setAdditionalneeds(null).setTotalprice(0);

        // Act
        Response response = BookingAPI.newBooking(bookingBody);

        // Assert
        assertEquals(200, response.getStatusCode());
    }
}
