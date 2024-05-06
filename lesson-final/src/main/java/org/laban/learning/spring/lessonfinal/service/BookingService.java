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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

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
        return bookingRepository.save(upsertBooking);
    }

    public BookingListDTO getBookingListDTOForHotel(@Nonnull Long hotelId) {
        return bookingMapper.entityListToBookingListDTO(getAllBookingsForHotel(hotelId));
    }

    public List<Booking> getAllBookingsForHotel(@Nonnull Long hotelId) {
        return bookingRepository.findAll(SpecificationUtils.bookingsOfHotel(hotelId));
    }

    public BookingListDTO getBookingListDTOForUser(@Nonnull Long userId) {
        return bookingMapper.entityListToBookingListDTO(getAllBookingsForUser(userId));
    }

    public List<Booking> getAllBookingsForUser(@Nonnull Long userId) {
        return bookingRepository.findAll(SpecificationUtils.bookingsOfUser(userId));
    }
}