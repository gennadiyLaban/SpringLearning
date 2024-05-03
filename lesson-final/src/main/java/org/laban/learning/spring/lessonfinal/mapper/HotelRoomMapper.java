package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.HotelRoom;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { BookingMapper.class, HotelMapper.class }
)
public interface HotelRoomMapper {
    HotelRoomDTO entityToDTO(HotelRoom hotelRoom);

    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "findHotelByDTO")
    HotelRoom dtoToEntityForCreation(HotelRoomDTO upsertHotelRoom);

    @Mapping(target = "hotel", ignore = true)
    HotelRoom dtoToEntityForUpdate(HotelRoomDTO upsertHotelRoom);
}
