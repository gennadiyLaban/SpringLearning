package org.laban.learning.spring.lessonfinal.web.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class HotelRoomListDTO {
    private List<HotelRoomDTO> rooms;
    private Integer page;
    private Integer pageSize;
    private Integer pageCount;
}
