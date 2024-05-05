package org.laban.learning.spring.lessonfinal.web.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lessonfinal.security.AppUserDetails;
import org.laban.learning.spring.lessonfinal.service.HotelService;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelListDTO;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.MessageFormat;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hotel")
@Validated
public class HotelController {
    private final HotelService hotelService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> findHotelById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelService.getHotelDtoById(id));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/list")
    public ResponseEntity<HotelListDTO> findAllHotels(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size
    ) {
        return ResponseEntity.ok(hotelService.getAllHotelsDTO(PageRequest.of(page, size)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createHotel(
            @RequestBody @Validated(ValidationGroup.Create.class)
            HotelDTO hotelDTO
    ) {
        var createdId = hotelService.createHotel(hotelDTO);
        return ResponseEntity
                .created(URI.create(MessageFormat.format("/api/v1/hotel/{0}", createdId)))
                .build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHotel(
            @RequestBody @Validated(ValidationGroup.Update.class)
            HotelDTO hotelDTO
    ) {
        hotelService.updateHotel(hotelDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Long id) {
        hotelService.deleteHotelById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PatchMapping("/{hotelId}/mark")
    public ResponseEntity<Void> markHotel(
            @PathVariable("hotelId") Long hotelId,
            @RequestParam("rating") @Min(1) @Max(5) Integer rating,
            @AuthenticationPrincipal AppUserDetails userDetails
            ) {
        hotelService.markHotel(hotelId, rating, userDetails);
        return ResponseEntity.noContent().build();
    }
}
