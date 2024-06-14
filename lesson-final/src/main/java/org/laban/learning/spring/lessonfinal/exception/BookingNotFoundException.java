package org.laban.learning.spring.lessonfinal.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookingNotFoundException extends RuntimeException {
    private final Long bookingId;
}
