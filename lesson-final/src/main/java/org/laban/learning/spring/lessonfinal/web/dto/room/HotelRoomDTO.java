package org.laban.learning.spring.lessonfinal.web.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingRecordDTO;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class HotelRoomDTO {
    private Long id;
    private String name;
    private String description;
    private Integer roomNumber;
    private BigDecimal price;
    private Integer maxCapacity;
    private HotelDTO hotel;
    private List<BookingRecordDTO> bookings;
}
