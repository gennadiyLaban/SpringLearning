package org.laban.learning.spring.lessonfinal.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.lessonfinal.service.HotelRoomService;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomDTO;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomListDTO;
import org.laban.learning.spring.lessonfinal.web.dto.room.HotelRoomListRequestDTO;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/room")
public class HotelRoomController {
    private final HotelRoomService hotelRoomService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<HotelRoomDTO> getHotelRoomById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelRoomService.getHotelRoomDTOById(id));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/list")
    public ResponseEntity<HotelRoomListDTO> findAllHotelRooms(
            @RequestBody @Valid
            HotelRoomListRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                hotelRoomService.getAllHotelsDTO(requestDTO)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createHotelRoom(
            @RequestBody
            @Validated(ValidationGroup.Create.class)
            HotelRoomDTO hotelRoomDTO
    ) {
        var createdId = hotelRoomService.createHotelRoom(hotelRoomDTO);
        return ResponseEntity.created(URI.create("/api/v1/room/" + createdId)).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHotelRoom(
            @RequestBody
            @Validated(ValidationGroup.Update.class)
            HotelRoomDTO hotelRoomDTO
    ) {
        hotelRoomService.updateHotelRoom(hotelRoomDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotelRoom(@PathVariable("id") Long id) {
        hotelRoomService.deleteHotelRoom(id);
        return ResponseEntity.noContent().build();
    }
}
