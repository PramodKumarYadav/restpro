package org.powertester.booking;

import lombok.Data;
import org.powertester.booking.entitites.Booking;

@Data
public class BookingResponse {
    private long bookingid;
    private Booking booking;
}
