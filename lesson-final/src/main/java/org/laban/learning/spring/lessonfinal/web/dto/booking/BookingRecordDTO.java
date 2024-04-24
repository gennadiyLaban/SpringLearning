package org.laban.learning.spring.lessonfinal.web.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BookingRecordDTO {
    private Long id;
    private Instant start;
    private Instant end;
    private Long roomId;
}
