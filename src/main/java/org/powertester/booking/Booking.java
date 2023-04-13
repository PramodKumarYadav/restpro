package org.powertester.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
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
        Bookingdates bookingdates = Bookingdates.builder()
                .setCheckin("2018-01-01")
                .setCheckout("2019-01-01")
                .build();

        Booking booking = Booking.builder()
                .setFirstname("Jim")
                .setLastname("Brown")
                .setTotalprice(111)
                .setDepositpaid(true)
                .setBookingdates(bookingdates)
                .setAdditionalneeds("Breakfast")
                .build();

        log.info("bookingBody: {}", booking);
        return booking;
    }
}
