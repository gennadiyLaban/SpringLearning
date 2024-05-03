package org.laban.learning.spring.lessonfinal.mapper;

import jakarta.annotation.Nullable;
import org.laban.learning.spring.lessonfinal.model.Booking;
import org.laban.learning.spring.lessonfinal.model.HotelRoom;
import org.laban.learning.spring.lessonfinal.model.User;
import org.laban.learning.spring.lessonfinal.service.HotelRoomService;
import org.laban.learning.spring.lessonfinal.service.UserService;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public abstract class BookingMapperDelegate implements BookingMapper {
    @Autowired
    @Qualifier("delegate")
    private BookingMapper bookingMapperDelegate;

    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private HotelRoomService hotelRoomService;

    @Override
    public Booking dtoToEntity(@Nullable BookingDTO upsertBookingDto) {
        if (upsertBookingDto == null) {
            return null;
        }

        var booking = bookingMapperDelegate.dtoToEntity(upsertBookingDto);
        if (booking == null) {
            return null;
        }

        var builder = booking.toBuilder();
        return builder
                .user(getUserById(upsertBookingDto.getUserId()))
                .room(getHotelRoomById(upsertBookingDto.getRoomId()))
                .startDate(truncateToDays(upsertBookingDto.getStartDate()))
                .endDate(truncateToDays(upsertBookingDto.getEndDate()))
                .build();
    }

    @Nullable
    private User getUserById(@Nullable Long userId) {
        if (userId == null) {
            return null;
        }

        return userService.getUserById(userId);
    }

    @Nullable
    private HotelRoom getHotelRoomById(@Nullable Long hotelRoomId) {
        if (hotelRoomId == null) {
            return null;
        }

        return hotelRoomService.getHotelRoomById(hotelRoomId);
    }

    @Nullable
    private Instant truncateToDays(@Nullable Instant instant) {
        if (instant == null) {
            return null;
        }

        return instant.truncatedTo(ChronoUnit.DAYS);
    }
}
