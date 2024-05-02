package org.laban.learning.spring.lessonfinal.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lessonfinal.service.HotelRoomService;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomDTO;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/room")
public class HotelRoomController {
    private final HotelRoomService hotelRoomService;

    @GetMapping("/{id}")
    public ResponseEntity<HotelRoomDTO> getHotelRoomById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelRoomService.getHotelRoomDTOById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createHotelRoom(
            @RequestBody
            @Validated(ValidationGroup.Create.class)
            HotelRoomDTO hotelRoomDTO
    ) {
        var createdId = hotelRoomService.createHotelRoom(hotelRoomDTO);
        return ResponseEntity.created(URI.create("/api/v1/room/" + createdId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHotelRoom(
            @RequestBody
            @Validated(ValidationGroup.Update.class)
            HotelRoomDTO hotelRoomDTO
    ) {
        hotelRoomService.updateHotelRoom(hotelRoomDTO);
        return ResponseEntity.noContent().build();
    }
}
