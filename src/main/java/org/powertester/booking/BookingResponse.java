package org.powertester.booking;

import lombok.Data;

@Data
public class BookingResponse {
  private long bookingid;
  private Booking booking;
}
