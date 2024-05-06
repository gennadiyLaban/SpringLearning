package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.HotelRoom;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomDTO;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

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

    default List<HotelRoomDTO> entityListToDTOList(List<HotelRoom> rooms) {
        return rooms.stream().map(this::entityToDTO).toList();
    }

    @Mapping(target = "rooms", source = "page.content")
    @Mapping(target = "page", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    @Mapping(target = "pageCount", source = "page.totalPages")
    HotelRoomListDTO entityToHotelRoomListDTO(Page<HotelRoom> page);
}
