package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.exception.HotelNotFoundException;
import org.laban.learning.spring.lessonfinal.mapper.HotelMapper;
import org.laban.learning.spring.lessonfinal.model.Hotel;
import org.laban.learning.spring.lessonfinal.repository.HotelRepository;
import org.laban.learning.spring.lessonfinal.utils.BeanUtils;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

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

    @Transactional(readOnly = true)
    public HotelListDTO getAllHotelsDTO(@Nonnull Pageable pageable) {
        return hotelMapper.hotelPageToHotelDTOlist(getAllHotels(pageable));
    }

    @Transactional(readOnly = true)
    public Page<Hotel> getAllHotels(@Nonnull Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    @Transactional
    public long createHotel(@Nonnull HotelDTO hotelDTO) {
        return createHotel(hotelMapper.hotelDTOtoHotel(hotelDTO)).getId();
    }

    @Transactional
    public Hotel createHotel(@Nonnull Hotel upsertHotel) {
        return hotelRepository.save(upsertHotel);
    }

    @Transactional
    public void updateHotel(@Nonnull HotelDTO hotelDTO) {
        updateHotel(hotelMapper.hotelDTOtoHotel(hotelDTO));
    }

    @Transactional
    public void updateHotel(@Nonnull Hotel upsertHotel) {
        var existedHotel = getHotelById(upsertHotel.getId());
        var existedAddress = existedHotel.getAddress();
        BeanUtils.copyNonNullProperties(upsertHotel.getAddress(), existedAddress);
        BeanUtils.copyNonNullProperties(upsertHotel, existedHotel, Set.of(
                Hotel.Fields.address, Hotel.Fields.rating, Hotel.Fields.numberOfRating
        ));
    }

    @Transactional
    public void deleteHotelById(@Nonnull Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new HotelNotFoundException(id);
        }
        hotelRepository.deleteById(id);
    }
}
