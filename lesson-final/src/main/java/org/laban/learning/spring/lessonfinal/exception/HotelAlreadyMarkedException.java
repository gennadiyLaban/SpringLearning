package org.laban.learning.spring.lessonfinal.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HotelAlreadyMarkedException extends RuntimeException {
    private final Long hotelId;
    private final Long userId;
}
