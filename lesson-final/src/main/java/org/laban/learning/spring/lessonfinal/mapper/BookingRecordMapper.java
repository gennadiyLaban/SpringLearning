package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.Booking;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingRecordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingRecordMapper {
    @Mapping(target = "roomId", source = "room.id")
    BookingRecordDTO entityToDto(Booking booking);

    default List<BookingRecordDTO> entityListToDtoList(List<Booking> bookings) {
        return bookings.stream().map(this::entityToDto).toList();
    }
}
