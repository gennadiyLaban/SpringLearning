package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.exception.HotelRoomNotFoundException;
import org.laban.learning.spring.lessonfinal.mapper.HotelRoomMapper;
import org.laban.learning.spring.lessonfinal.model.HotelRoom;
import org.laban.learning.spring.lessonfinal.repository.HotelRoomRepository;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HotelRoomService {
    private final HotelRoomRepository hotelRoomRepository;
    private final HotelRoomMapper hotelRoomMapper;

    @Transactional(readOnly = true)
    public HotelRoomDTO getHotelRoomDTOById(@Nonnull Long id) {
        return hotelRoomMapper.entityToDTO(getHotelRoomById(id));
    }

    @Transactional(readOnly = true)
    public HotelRoom getHotelRoomById(@Nonnull Long id) {
        return findHotelRoomById(id)
                .orElseThrow(() -> new HotelRoomNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<HotelRoom> findHotelRoomById(@Nonnull Long id) {
        return hotelRoomRepository.findById(id);
    }
}
