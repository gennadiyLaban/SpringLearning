package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.kafka.HotelRoomBookedEvent;
import org.laban.learning.spring.lessonfinal.model.kafka.UserRegisteredEvent;
import org.laban.learning.spring.lessonfinal.model.statistic.BookingStatisticRecord;
import org.laban.learning.spring.lessonfinal.model.statistic.UserRegistrationStatisticRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StatisticMapper {
    @Mapping(target = "id", source = "bookingId")
    BookingStatisticRecord eventToBookingStatisticRecord(HotelRoomBookedEvent event);


    @Mapping(target = "id", source = "userId")
    UserRegistrationStatisticRecord eventToUserRegistrationStatisticRecord(UserRegisteredEvent event);
}
