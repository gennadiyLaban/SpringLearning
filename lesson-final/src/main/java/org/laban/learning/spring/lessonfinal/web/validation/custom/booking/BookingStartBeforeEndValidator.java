package org.laban.learning.spring.lessonfinal.web.validation.custom.booking;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingDTO;

import java.time.Period;
import java.time.ZoneOffset;

public class BookingStartBeforeEndValidator implements ConstraintValidator<BookingStartBeforeEnd, BookingDTO> {
    private static final ZoneOffset TIMEZONE_UTC = ZoneOffset.UTC;

    @Override
    public boolean isValid(BookingDTO value, ConstraintValidatorContext context) {
        var start = value.getStartDate();
        var end = value.getEndDate();
        if (start == null || end == null) {
            return true;
        }

        var startDate = start.atZone(TIMEZONE_UTC).toLocalDate();
        var endDate = end.atZone(TIMEZONE_UTC).toLocalDate();
        return Period.between(startDate, endDate).getDays() >= 1;
    }
}
