package org.laban.learning.spring.lessonfinal.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.exception.BookingDatesAlreadyBookedException;
import org.laban.learning.spring.lessonfinal.exception.BookingNotFoundException;
import org.laban.learning.spring.lessonfinal.mapper.BookingMapper;
import org.laban.learning.spring.lessonfinal.model.Booking;
import org.laban.learning.spring.lessonfinal.repository.BookingRepository;
import org.laban.learning.spring.lessonfinal.utils.SpecificationUtils;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingDTO;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    private final StatisticService statisticService;

    @Transactional(readOnly = true)
    public BookingDTO getBookingDtoById(@Nonnull Long id) {
        return bookingMapper.entityToDto(getBookingById(id));
    }

    @Transactional(readOnly = true)
    public Booking getBookingById(@Nonnull Long id) {
        return findBookingById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Optional<Booking> findBookingById(@Nonnull Long id) {
        return bookingRepository.findById(id);
    }

    @Transactional
    public Long createBooking(@Nonnull BookingDTO upsertBookingDto) {
        return createBooking(bookingMapper.dtoToEntity(upsertBookingDto)).getId();
    }

    @Transactional
    public Booking createBooking(@Nonnull Booking upsertBooking) {
        if (bookingRepository.isBooked(upsertBooking)) {
            throw new BookingDatesAlreadyBookedException(upsertBooking.getStartDate(), upsertBooking.getEndDate());
        }
        var savedBooking = bookingRepository.save(upsertBooking);
        statisticService.sendHotelRoomBooked(savedBooking);
        return savedBooking;
    }

    @Transactional(readOnly = true)
    public BookingListDTO findBookingListDTOForHotel(@Nonnull Long hotelId, @Nonnull Pageable pageable) {
        return bookingMapper.entityPageToBookingListDTO(findAllBookings(
                SpecificationUtils.bookingsOfHotel(hotelId),
                pageable
        ));
    }

    @Transactional(readOnly = true)
    public BookingListDTO findBookingListDTOForUser(@Nonnull Long userId, @Nonnull Pageable pageable) {
        return bookingMapper.entityPageToBookingListDTO(findAllBookings(
                SpecificationUtils.bookingsOfUser(userId),
                pageable
        ));
    }

    @Transactional(readOnly = true)
    public Page<Booking> findAllBookings(@Nonnull Specification<Booking> specification, @Nonnull Pageable pageable) {
        return bookingRepository.findAll(specification, pageable);
    }
}
