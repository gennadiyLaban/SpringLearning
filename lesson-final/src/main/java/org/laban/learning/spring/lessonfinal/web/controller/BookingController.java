package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.service.BookingService;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingDTO;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingListDTO;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;
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
            @PathVariable("hotelId") Long hotelId
    ) {
        return ResponseEntity.ok(bookingService.getBookingListDTOForHotel(hotelId));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/user/{userId}/list")
    public ResponseEntity<BookingListDTO> getAllUserBookings(
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok(bookingService.getBookingListDTOForUser(userId));
    }

}
