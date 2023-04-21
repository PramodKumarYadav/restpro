package booking;

import static org.apache.http.HttpStatus.*;
import static org.powertester.auth.Scope.ADMIN;
import static org.powertester.auth.Scope.GUEST;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.powertester.annotations.FailingTest;
import org.powertester.booking.Booking;
import org.powertester.booking.BookingAPI;
import setup.TestSetup;

@Slf4j
public class BookingTests extends TestSetup {
  public static final String READ_UPDATE_BOOKING_SCHEMA_FILE_PATH =
      "schemas/read-update-booking-schema.json";
  public static final String CREATE_BOOKING_SCHEMA_FILE_PATH = "schemas/create-booking-schema.json";
  private Long bookingId;
  private Booking booking;

  // Setup: Create a booking
  @BeforeEach
  public void setup() {
    // Arrange
    booking = Booking.getInstance();
    // Act
    Response response = BookingAPI.useAs(ADMIN).newBooking(booking);

    // Assert
    VerifyBookingResponse.assertThat(response)
        .statusCodeIs(SC_OK)
        .matchesSchema(CREATE_BOOKING_SCHEMA_FILE_PATH)
        .postHasBooking(booking)
        .assertAll();

    // Set bookingId
    bookingId = response.body().jsonPath().getLong("bookingid");
  }

  // TearDown: Delete the booking
  @AfterEach
  public void tearDown() {
    Response response = BookingAPI.useAs(ADMIN).deleteBooking(bookingId);

    // Assert (It should ideally be 200 but this application has a bug and it gives 201)
    VerifyBookingResponse.assertThat(response).statusCodeIs(SC_CREATED).assertAll();
  }

  @Nested
  class AdminUser {
    @Test
    void assertThatAUserCanGetAnExistingBooking() {
      // Act
      Response response = BookingAPI.useAs(GUEST).getBooking(bookingId);

      // Assert
      VerifyBookingResponse.assertThat(response)
          .statusCodeIs(SC_OK)
          .matchesSchema(READ_UPDATE_BOOKING_SCHEMA_FILE_PATH)
          .hasBooking(booking)
          .assertAll();
    }

    @Test
    void assertThatAUserCanUpdateAnExistingBooking() {
      // Arrange
      booking.setFirstname("Vinod");

      // Act
      Response response = BookingAPI.useAs(ADMIN).updateBooking(booking, bookingId);

      // Assert
      VerifyBookingResponse.assertThat(response)
          .statusCodeIs(SC_OK)
          .matchesSchema("schemas/read-update-booking-schema.json")
          .hasBooking(booking)
          .assertAll();
    }

    @FailingTest
    void assertThatAUserCanPartiallyUpdateAnExistingBooking() {
      // Arrange
      Booking partialBooking =
          Booking.builder().setFirstname("Pramod").setLastname("Yadav").build();

      log.info("partialBookingBody: {}", partialBooking);

      // Act
      Response response = BookingAPI.useAs(ADMIN).patchBooking(partialBooking, bookingId);

      // Assert
      Booking expectedBooking =
          booking
              .setFirstname(partialBooking.getFirstname())
              .setLastname(partialBooking.getLastname());

      VerifyBookingResponse.assertThat(response)
          .statusCodeIs(SC_OK)
          .matchesSchema("schemas/read-update-booking-schema.json")
          .hasBooking(expectedBooking)
          .assertAll();
    }
  }

  @Nested
  class GuestUser {
    @Test
    void assertThatAUserCanNotUpdateAnExistingBookingWithoutValidAuthentication() {
      // Arrange
      booking.setFirstname("Vinod");

      // Act
      Response response = BookingAPI.useAs(GUEST).updateBooking(booking, bookingId);

      // Assert
      VerifyBookingResponse.assertThat(response).statusCodeIs(SC_FORBIDDEN).assertAll();
    }
  }
}
