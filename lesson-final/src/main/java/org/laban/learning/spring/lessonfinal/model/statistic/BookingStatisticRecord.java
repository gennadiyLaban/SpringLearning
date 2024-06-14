package org.laban.learning.spring.lessonfinal.model.statistic;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Document(collection = "bookings")
public class BookingStatisticRecord {
    @Id
    private String id;

    private Long bookingId;
    private Long userId;
    private Long hotelRoomId;
    private Instant startBooking;
    private Instant endBooking;
    private Instant timestamp;
}
