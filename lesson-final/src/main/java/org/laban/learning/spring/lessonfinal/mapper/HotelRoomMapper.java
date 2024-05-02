package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.HotelRoom;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomDTO;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { BookingRecordMapper.class, HotelMapper.class }
)
public interface HotelRoomMapper {
    HotelRoomDTO entityToDTO(HotelRoom hotelRoom);

    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "findHotelByDTO")
    HotelRoom dtoToEntity(HotelRoomDTO upsertHotelRoom);
}
