package org.powertester.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.powertester.booking.entitites.Bookingdates;

@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder(setterPrefix = "set")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
  private String firstname;
  private String lastname;
  private long totalprice;
  private boolean depositpaid;
  private Bookingdates bookingdates;
  private String additionalneeds;

  public static Booking getInstance() {
    Bookingdates bookingdates =
        Bookingdates.builder()
            .setCheckin(new Faker().date().future(1, TimeUnit.DAYS, "YYYY-MM-dd"))
            .setCheckout(new Faker().date().future(6, TimeUnit.DAYS, "YYYY-MM-dd"))
            .build();

    Booking booking =
        Booking.builder()
            .setFirstname(new Faker().name().firstName())
            .setLastname(new Faker().name().lastName())
            .setTotalprice(new Faker().number().numberBetween(1, 1000))
            .setDepositpaid(true)
            .setBookingdates(bookingdates)
            .setAdditionalneeds(new Faker().food().dish())
            .build();

    log.info("bookingBody: {}", booking);
    return booking;
  }
}
