package org.laban.learning.spring.lessonfinal.model.kafka;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class HotelRoomBookedEvent {
    private Long bookingId;
    private Long userId;
    private Long hotelRoomId;
    private Instant startBooking;
    private Instant endBooking;
    private Instant timestamp;
}
