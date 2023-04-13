package booking;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.powertester.annotations.FailingTest;
import org.powertester.booking.Booking;
import org.powertester.booking.BookingAPI;
import setup.TestSetup;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
public class BookingTests extends TestSetup {
    private Long bookingId;
    private Booking booking;

    // Setup: Create a booking
    @BeforeEach
    public void setup() {
        // Arrange
        booking = Booking.getInstance();
        // Act
        Response response = BookingAPI.newBooking(booking);

        // Assert
        VerifyBookingResponse.assertThat(response)
                .statusCodeIs(SC_OK)
                .postHasBooking(booking)
                .assertAll();

        // Set bookingId
        bookingId = response.body().jsonPath().getLong("bookingid");
    }

    // TearDown: Delete the booking
    @AfterEach
    public void tearDown() {
        Response response = BookingAPI.deleteBooking(bookingId);

        // Assert (It should ideally be 200 but this application has a bug and it gives 201)
        VerifyBookingResponse.assertThat(response)
                .statusCodeIs(SC_CREATED)
                .assertAll();
    }

    @Test
    void assertThatAUserCanGetAnExistingBooking() {
        // Act
        Response response = BookingAPI.getBooking(bookingId);

        // Assert
        VerifyBookingResponse.assertThat(response)
                .statusCodeIs(SC_OK)
                .hasBooking(booking)
                .assertAll();
    }

    @Test
    void assertThatAUserCanUpdateAnExistingBooking() {
        // Arrange
        booking.setFirstname("Vinod");

        // Act
        Response response = BookingAPI.updateBooking(booking, bookingId);

        // Assert
        VerifyBookingResponse.assertThat(response)
                .statusCodeIs(SC_OK)
                .hasBooking(booking)
                .assertAll();
    }

    @FailingTest
    void assertThatAUserCanPartiallyUpdateAnExistingBooking() {
        // Arrange
        Booking partialBooking = Booking.builder()
                .setFirstname("Pramod")
                .setLastname("Yadav")
                .build();

        log.info("partialBookingBody: {}", partialBooking);

        // Act
        Response response = BookingAPI.patchBooking(partialBooking, bookingId);

        // Assert
        Booking expectedBooking = booking
                .setFirstname(partialBooking.getFirstname())
                .setLastname(partialBooking.getLastname());

        VerifyBookingResponse.assertThat(response)
                .statusCodeIs(SC_OK)
                .containsValue("Pramod")
                .containsValue("Yadav")
                .hasBooking(expectedBooking)
                .assertAll();
    }
}
