package org.laban.learning.spring.lessonfinal.web.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.ConvertGroup;
import lombok.*;
import org.laban.learning.spring.lessonfinal.model.HotelRoom;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingDTO;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
import org.laban.learning.spring.lessonfinal.web.validation.custom.NullOrNotBlank;
import org.laban.learning.spring.lessonfinal.web.validation.group.ValidationGroup;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class HotelRoomDTO {
    @Null(groups = ValidationGroup.Create.class)
    @NotNull(groups = ValidationGroup.Update.class)
    private Long id;

    @NotBlank(groups = ValidationGroup.Create.class)
    @NullOrNotBlank(groups = ValidationGroup.Update.class)
    private String name;

    @NotBlank(groups = ValidationGroup.Create.class)
    @NullOrNotBlank(groups = ValidationGroup.Update.class)
    private String description;

    @NotNull(groups = ValidationGroup.Create.class)
    @Positive(groups = { ValidationGroup.Create.class, ValidationGroup.Update.class})
    private Integer roomNumber;

    @NotNull(groups = ValidationGroup.Create.class)
    @PositiveOrZero(groups = { ValidationGroup.Create.class, ValidationGroup.Update.class})
    private BigDecimal price;

    @NotNull(groups = ValidationGroup.Create.class)
    @Positive(groups = { ValidationGroup.Create.class, ValidationGroup.Update.class })
    private Integer maxCapacity;

    @Valid
    @NotNull(groups = ValidationGroup.Create.class)
    @ConvertGroup.List(value = {
            @ConvertGroup(from = ValidationGroup.Create.class, to = ValidationGroup.NestedObject.class),
            @ConvertGroup(from = ValidationGroup.Update.class, to = ValidationGroup.NestedObject.class),
    })
    @JsonDeserialize(converter = HotelRoom.NestedConverter.class)
    private HotelDTO hotel;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<BookingDTO> bookings;
}
