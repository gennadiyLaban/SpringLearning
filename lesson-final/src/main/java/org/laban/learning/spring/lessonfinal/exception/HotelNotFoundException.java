package org.laban.learning.spring.lessonfinal.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HotelNotFoundException extends RuntimeException {
    private final Long hotelId;

    public HotelNotFoundException() {
        super();
        hotelId = null;
    }
}
