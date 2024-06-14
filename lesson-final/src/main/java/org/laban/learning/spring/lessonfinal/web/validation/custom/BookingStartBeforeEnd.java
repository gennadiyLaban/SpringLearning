package org.laban.learning.spring.lessonfinal.web.validation.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.laban.learning.spring.lessonfinal.web.validation.custom.booking.BookingStartBeforeEndValidator;
import org.laban.learning.spring.lessonfinal.web.validation.custom.room.BookingStartBeforeEndForHotelRoomRequestValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( {ElementType.TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {
        BookingStartBeforeEndValidator.class,
        BookingStartBeforeEndForHotelRoomRequestValidator.class
})
public @interface BookingStartBeforeEnd {
    String message() default "startDate must be before endDate";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}
