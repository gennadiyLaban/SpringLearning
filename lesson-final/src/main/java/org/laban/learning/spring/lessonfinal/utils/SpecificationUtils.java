package org.laban.learning.spring.lessonfinal.utils;

import jakarta.annotation.Nonnull;
import org.laban.learning.spring.lessonfinal.model.Booking;
import org.laban.learning.spring.lessonfinal.model.Hotel;
import org.laban.learning.spring.lessonfinal.model.HotelRoom;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {
    private SpecificationUtils() {
        throw new UnsupportedOperationException("This is utils class!");
    }

    public static Specification<Booking> bookingsOfHotel(@Nonnull Long hotelId) {
        return Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get(Booking.Fields.room)
                        .get(HotelRoom.Fields.hotel)
                        .get(Hotel.Fields.id),
                hotelId
        )));
    }
}
