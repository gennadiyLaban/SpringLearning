package org.laban.learning.spring.lessonfinal.web.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BookingListDTO {
    private List<BookingDTO> bookings;
    private Integer page;
    private Integer pageSize;
    private Integer pageCount;
}
