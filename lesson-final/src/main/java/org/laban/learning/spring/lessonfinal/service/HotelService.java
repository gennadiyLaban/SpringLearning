package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.exception.HotelNotFoundException;
import org.laban.learning.spring.lessonfinal.mapper.HotelMapper;
import org.laban.learning.spring.lessonfinal.model.Hotel;
import org.laban.learning.spring.lessonfinal.repository.HotelRepository;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Transactional(readOnly = true)
    public HotelDTO getHotelDtoById(@Nonnull Long id) {
        return hotelMapper.hotelToHotelDTO(getHotelById(id));
    }

    @Transactional(readOnly = true)
    public Hotel getHotelById(@Nonnull Long id) {
        return findHotelById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<Hotel> findHotelById(@Nonnull Long id) {
        return hotelRepository.findById(id);
    }


}
