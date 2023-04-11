package org.powertester.booking.entitites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor

@Builder(setterPrefix = "set")
@AllArgsConstructor
public class Bookingdates {
    private String checkin;
    private String checkout;
}
