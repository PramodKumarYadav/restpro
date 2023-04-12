package booking;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.powertester.booking.BookingAPI;
import org.powertester.booking.BookingBody;
import setup.TestSetup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class BookingTests extends TestSetup {
    private String bookingId;
    private BookingBody bookingBody;

    // Setup: Create a booking
    @BeforeEach
    public void setup() {
        // Arrange
        bookingBody = BookingBody.getInstance();

        // Act
        Response response = BookingAPI.newBooking(bookingBody);

        // Assert
        assertEquals(200, response.getStatusCode());

        // Set bookingId
        bookingId = response.body().jsonPath().getString("bookingid");
    }

    // TearDown: Delete the booking
    @AfterEach
    public void tearDown() {
        Response response = BookingAPI.deleteBooking(bookingId);

        // Assert (It should ideally be 200 but this application has a bug and it gives 201)
        assertEquals(201, response.getStatusCode());
    }

    @Test
    void assertThatAUserCanFetchAnExistingBooking() {
        // Act
        Response response = BookingAPI.getBooking(bookingId);
        String firstname = response.body().jsonPath().getString("firstname");

        // Assert
        assertEquals(200, response.getStatusCode());
        assertEquals("Jim", firstname);
    }

    @Test
    void assertThatAUserCanUpdateAnExistingBooking() {
        // Arrange
        bookingBody.setFirstname("Vinod");

        // Act
        Response response = BookingAPI.updateBooking(bookingBody, bookingId);
        String firstname = response.body().jsonPath().getString("firstname");

        // Assert
        assertEquals(200, response.getStatusCode());
        assertEquals("Vinod", firstname);
    }

    @Test
    void assertThatAUserCanPartiallyUpdateAnExistingBooking() {
        // Arrange
        BookingBody partialBookingBody = BookingBody.builder()
                .setFirstname("Pramod")
                .setLastname("Yadav")
                .build();

        log.info("partialBookingBody: {}", partialBookingBody);

        // Act
        Response response = BookingAPI.patchBooking(partialBookingBody, bookingId);
        String firstname = response.body().jsonPath().getString("firstname");
        String lastname = response.body().jsonPath().getString("lastname");

        // Assert
        assertEquals(200, response.getStatusCode());
        assertEquals("Pramod", firstname);
        assertEquals("Yadav", lastname);
    }
}
