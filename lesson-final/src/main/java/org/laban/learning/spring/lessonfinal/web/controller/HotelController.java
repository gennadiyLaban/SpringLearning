package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.service.HotelService;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> findHotelById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelService.getHotelDtoById(id));
    }
}
