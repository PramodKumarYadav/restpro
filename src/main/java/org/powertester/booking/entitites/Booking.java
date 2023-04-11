package org.powertester.booking.entitites;

import lombok.Data;

@Data
public class Booking {
    private String firstname;
    private String lastname;
    private long totalprice;
    private boolean depositpaid;
    private Bookingdates bookingdates;
    private String additionalneeds;
}
