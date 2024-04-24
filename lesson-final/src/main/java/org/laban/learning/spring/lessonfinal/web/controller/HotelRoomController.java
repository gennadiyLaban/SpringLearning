package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lessonfinal.service.HotelRoomService;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/room")
public class HotelRoomController {
    private final HotelRoomService hotelRoomService;

    @GetMapping("/{id}")
    public ResponseEntity<HotelRoomDTO> getHotelRoomById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelRoomService.getHotelRoomDTOById(id));
    }

}
