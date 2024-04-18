package org.laban.learning.spring.lessonfinal.web.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.laban.learning.spring.lessonfinal.model.Address;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class HotelDTO {
    private Long id;
    private String name;
    private Address address;
    private Long distanceFromCenter;
    private BigDecimal rating;
    private Integer numberOfRating;
}
