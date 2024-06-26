package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.exception.HotelRoomNotFoundException;
import org.laban.learning.spring.lessonfinal.mapper.HotelRoomMapper;
import org.laban.learning.spring.lessonfinal.model.HotelRoom;
import org.laban.learning.spring.lessonfinal.repository.HotelRoomRepository;
import org.laban.learning.spring.lessonfinal.utils.BeanUtils;
import org.laban.learning.spring.lessonfinal.utils.SpecificationUtils;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomDTO;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomListDTO;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomListRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class HotelRoomService {
    private final static Set<String> EXCLUDED_FIELDS_FROM_UPDATE = Set.of(
            HotelRoom.Fields.hotel, HotelRoom.Fields.bookings);

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

    @Transactional(readOnly = true)
    public HotelRoomListDTO getAllHotelsDTO(HotelRoomListRequestDTO requestDTO) {
        var page = requestDTO.getPage();
        var filter =  requestDTO.getFilter();
        return hotelRoomMapper.entityToHotelRoomListDTO(getAllHotelsDTO(
                PageRequest.of(page.getNumber(), page.getSize()),
                SpecificationUtils.hotelRoomsByFilter(filter)
        ));
    }

    @Transactional(readOnly = true)
    public Page<HotelRoom> getAllHotelsDTO(
            @Nonnull Pageable pageable,
            @Nonnull Specification<HotelRoom> specification
    ) {
        return hotelRoomRepository.findAll(specification, pageable);
    }

    @Transactional
    public Long createHotelRoom(@Nonnull HotelRoomDTO upsertHotelRoom) {
        return createHotelRoom(hotelRoomMapper.dtoToEntityForCreation(upsertHotelRoom));
    }

    @Transactional
    public Long createHotelRoom(@Nonnull HotelRoom upsertHotelRoom) {
        if (upsertHotelRoom.getId() != null) {
            throw new RuntimeException(MessageFormat.format(
                    "Hotel room with id={0} already exists", upsertHotelRoom.getId()));
        }

        return hotelRoomRepository.save(upsertHotelRoom).getId();
    }

    @Transactional
    public void updateHotelRoom(@Nonnull HotelRoomDTO upsertHotelRoom) {
        updateHotelRoom(hotelRoomMapper.dtoToEntityForUpdate(upsertHotelRoom));
    }

    @Transactional
    public void updateHotelRoom(@Nonnull HotelRoom upsertHotelRoom) {
        var existedHotelRoom = getHotelRoomById(upsertHotelRoom.getId());
        BeanUtils.copyNonNullProperties(upsertHotelRoom, existedHotelRoom, EXCLUDED_FIELDS_FROM_UPDATE);
        hotelRoomRepository.save(existedHotelRoom);
    }

    @Transactional
    public void deleteHotelRoom(@Nonnull Long id) {
        if (!hotelRoomRepository.existsById(id)) {
            throw new HotelRoomNotFoundException(id);
        }

        hotelRoomRepository.deleteById(id);
    }
}
