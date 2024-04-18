package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.model.Hotel;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {
    HotelDTO hotelToHotelDTO(Hotel hotel);

    @Mapping(target = "hotels", source = "page.content")
    @Mapping(target = "page", source = "page.number")
    @Mapping(target = "pageSize", source = "page.size")
    @Mapping(target = "pageCount", source = "page.totalPages")
    HotelListDTO hotelPageToHotelDTOlist(Page<Hotel> page);

    default List<HotelDTO> hotelListToListOfHotelDTO(List<Hotel> hotels) {
        return hotels.stream().map(this::hotelToHotelDTO).toList();
    }
}
