package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.Booking;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingDTO;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingListDTO;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

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

    @Mapping(target = "bookings", source = "page.content")
    @Mapping(target = "page", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    @Mapping(target = "pageCount", source = "page.totalPages")
    BookingListDTO entityPageToBookingListDTO(Page<Booking> page);

    default List<BookingDTO> entityListToDtoList(List<Booking> bookings) {
        return bookings.stream().map(this::entityToDto).toList();
    }

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    Booking dtoToEntity(BookingDTO upsertBookingDto);
}
