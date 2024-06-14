package org.laban.learning.spring.lessonfinal.mapper;

import org.laban.learning.spring.lessonfinal.exception.HotelNotFoundException;
import org.laban.learning.spring.lessonfinal.model.Hotel;
import org.laban.learning.spring.lessonfinal.service.HotelService;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

public abstract class HotelMapperDelegate implements HotelMapper {
    @Autowired
    @Qualifier("delegate")
    private HotelMapper hotelMapperDelegate;

    @Lazy
    @Autowired
    private HotelService hotelService;

    @Override
    public Hotel findHotelByDTO(HotelDTO hotelDTO) {
        if (hotelDTO == null) {
            return null;
        }

        if (hotelDTO.getId() == null) {
            throw new HotelNotFoundException();
        }

        return hotelService.getHotelById(hotelDTO.getId());
    }
}
