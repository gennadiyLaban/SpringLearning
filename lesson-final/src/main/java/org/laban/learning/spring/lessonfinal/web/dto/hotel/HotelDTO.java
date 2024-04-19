package org.laban.learning.spring.lessonfinal.web.dto.hotel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.laban.learning.spring.lessonfinal.model.Address;
import org.laban.learning.spring.lessonfinal.web.validation.custom.NullOrNotBlank;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class HotelDTO {
    @Null(groups = ValidationGroup.Create.class)
    @NotNull(groups = ValidationGroup.Update.class)
    private Long id;

    @NotBlank(groups = ValidationGroup.Create.class)
    @NullOrNotBlank(groups = ValidationGroup.Update.class)
    private String name;

    @Valid
    @NotNull(groups = ValidationGroup.Create.class)
    private Address address;

    @NotNull(groups = ValidationGroup.Create.class)
    @PositiveOrZero
    private Long distanceFromCenter;

    @NotNull(groups = ValidationGroup.Create.class)
    @Null(groups = ValidationGroup.Update.class)
    @PositiveOrZero
    private BigDecimal rating;

    @NotNull(groups = ValidationGroup.Create.class)
    @Null(groups = ValidationGroup.Update.class)
    @PositiveOrZero
    private Integer numberOfRating;
}
