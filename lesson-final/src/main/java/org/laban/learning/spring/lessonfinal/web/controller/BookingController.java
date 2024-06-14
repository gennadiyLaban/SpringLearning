package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.configuration.AppContestants;
import org.laban.learning.spring.lessonfinal.service.BookingService;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingDTO;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingListDTO;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private final BookingService bookingService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingDtoById(id));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    public ResponseEntity<Void> createBooking(
            @RequestBody
            @Validated(value = ValidationGroup.BookingCreate.class)
            BookingDTO booking
    ) {
        var createdId = bookingService.createBooking(booking);
        return ResponseEntity.created(URI.create("/api/v1/booking/" + createdId)).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/hotel/{hotelId}/list")
    public ResponseEntity<BookingListDTO> getAllHotelBookings(
            @PathVariable("hotelId") Long hotelId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = AppContestants.DEFAULT_PAGE_SIZE_STR) Integer size
    ) {
        return ResponseEntity.ok(bookingService.findBookingListDTOForHotel(
                hotelId,
                PageRequest.of(page, size)
        ));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/user/{userId}/list")
    public ResponseEntity<BookingListDTO> getAllUserBookings(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = AppContestants.DEFAULT_PAGE_SIZE_STR) Integer size
    ) {
        return ResponseEntity.ok(bookingService.findBookingListDTOForUser(
                userId,
                PageRequest.of(page, size)
        ));
    }

}
