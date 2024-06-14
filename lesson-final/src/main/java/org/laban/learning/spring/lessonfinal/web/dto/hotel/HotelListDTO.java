package org.laban.learning.spring.lessonfinal.web.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class HotelListDTO {
    private List<HotelDTO> hotels;
    private Integer page;
    private Integer pageSize;
    private Integer pageCount;
}
