package org.laban.learning.spring.lessonfinal.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class BookingDatesAlreadyBookedException extends RuntimeException {
    private final Instant startDate;
    private final Instant endDate;
}
