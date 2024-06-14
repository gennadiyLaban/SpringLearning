package org.laban.learning.spring.lessonfinal.web.validation.custom.room;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomListRequestDTO;
import org.laban.learning.spring.lessonfinal.web.validation.custom.BookingStartBeforeEnd;

import java.time.Period;
import java.time.ZoneOffset;

public class BookingStartBeforeEndForHotelRoomRequestValidator
        implements ConstraintValidator<BookingStartBeforeEnd, HotelRoomListRequestDTO.BookingDates> {
    private static final ZoneOffset TIMEZONE_UTC = ZoneOffset.UTC;

    @Override
    public boolean isValid(HotelRoomListRequestDTO.BookingDates value, ConstraintValidatorContext context) {
        var start = value.startDate();
        var end = value.endDate();
        if (start == null || end == null) {
            return true;
        }

        var startDate = start.atZone(TIMEZONE_UTC).toLocalDate();
        var endDate = end.atZone(TIMEZONE_UTC).toLocalDate();
        return Period.between(startDate, endDate).getDays() >= 1;
    }
}
