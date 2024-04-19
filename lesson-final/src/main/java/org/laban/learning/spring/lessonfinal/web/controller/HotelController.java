package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.service.HotelService;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelListDTO;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.MessageFormat;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> findHotelById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelService.getHotelDtoById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<HotelListDTO> findAllHotels(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size
    ) {
        return ResponseEntity.ok(hotelService.getAllHotelsDTO(PageRequest.of(page, size)));
    }

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(
            @RequestBody @Validated(ValidationGroup.Create.class)
            HotelDTO hotelDTO
    ) {
        var createdId = hotelService.createHotel(hotelDTO);
        return ResponseEntity
                .created(URI.create(MessageFormat.format("/api/v1/hotel/{0}", createdId)))
                .build();
    }
}
