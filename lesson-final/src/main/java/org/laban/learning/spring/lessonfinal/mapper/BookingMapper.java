package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.Booking;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { UserMapper.class }
)
@DecoratedWith(BookingMapperDelegate.class)
public interface BookingMapper {
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "userId", source = "user.id")
    BookingDTO entityToDto(Booking booking);

    default List<BookingDTO> entityListToDtoList(List<Booking> bookings) {
        return bookings.stream().map(this::entityToDto).toList();
    }

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    Booking dtoToEntity(BookingDTO upsertBookingDto);
}
