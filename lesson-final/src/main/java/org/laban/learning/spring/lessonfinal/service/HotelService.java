package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.exception.HotelAlreadyMarkedException;
import org.laban.learning.spring.lessonfinal.exception.HotelNotFoundException;
import org.laban.learning.spring.lessonfinal.mapper.HotelMapper;
import org.laban.learning.spring.lessonfinal.model.Hotel;
import org.laban.learning.spring.lessonfinal.repository.HotelRepository;
import org.laban.learning.spring.lessonfinal.security.AppUserDetails;
import org.laban.learning.spring.lessonfinal.utils.BeanUtils;
import org.laban.learning.spring.lessonfinal.utils.SpecificationUtils;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelListDTO;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelListRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class HotelService {
    private final static Set<String> EXCLUDED_FIELDS_FROM_UPDATE = Set.of(
            Hotel.Fields.address, Hotel.Fields.rating, Hotel.Fields.numberOfRating);

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
    public HotelListDTO getAllHotelsDTO(@Nonnull HotelListRequestDTO requestDTO) {
        var page = requestDTO.getPage();
        var filter = requestDTO.getFilter();
        return hotelMapper.hotelPageToHotelDTOlist(getAllHotels(
                PageRequest.of(page.getNumber(), page.getSize()),
                SpecificationUtils.hotelsByFilter(filter)
        ));
    }

    @Transactional(readOnly = true)
    public Page<Hotel> getAllHotels(@Nonnull Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Hotel> getAllHotels(@Nonnull Pageable pageable, @Nonnull Specification<Hotel> specification) {
        return hotelRepository.findAll(specification, pageable);
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
        BeanUtils.copyNonNullProperties(upsertHotel, existedHotel, EXCLUDED_FIELDS_FROM_UPDATE);
    }

    @Transactional
    public void deleteHotelById(@Nonnull Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new HotelNotFoundException(id);
        }
        hotelRepository.deleteById(id);
    }

    @Transactional
    public void markHotel(@Nonnull Long hotelId, @Nonnull Integer newMark, @Nonnull AppUserDetails userDetails) {
        var hotel = getHotelById(hotelId);
        var userId = userDetails.getUserId();
        var isHotelAlreadyMarked = hotelRepository.isHotelAlreadyMarked(hotelId, userId);
        if (isHotelAlreadyMarked) {
            throw new HotelAlreadyMarkedException(hotelId, userId);
        }
        applyNewRating(hotel, userId, newMark);
    }

    private void applyNewRating(Hotel hotel, Long userId, Integer newMark) {
        hotelRepository.saveHotelRating(hotel.getId(), userId);

        var numberOfRating = BigDecimal.valueOf(hotel.getNumberOfRating() + 1);
        var totalRating = hotel.getRating().multiply(numberOfRating);
        totalRating = totalRating.subtract(hotel.getRating()).add(BigDecimal.valueOf(newMark));
        var newRating = totalRating.divide(numberOfRating, 1, RoundingMode.HALF_UP);

        hotel.setRating(newRating);
        hotel.setNumberOfRating(numberOfRating.intValueExact());
        hotelRepository.save(hotel);
    }
}
