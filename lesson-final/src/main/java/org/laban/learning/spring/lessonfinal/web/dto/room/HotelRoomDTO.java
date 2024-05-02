package org.laban.learning.spring.lessonfinal.web.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.ConvertGroup;
import lombok.*;
import org.laban.learning.spring.lessonfinal.web.dto.booking.BookingRecordDTO;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;
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
    private Long id;

    @NotBlank(groups = ValidationGroup.Create.class)
    private String name;

    @NotBlank(groups = ValidationGroup.Create.class)
    private String description;

    @NotNull(groups = ValidationGroup.Create.class)
    @Positive(groups = ValidationGroup.Create.class)
    private Integer roomNumber;

    @NotNull(groups = ValidationGroup.Create.class)
    @Positive(groups = ValidationGroup.Create.class)
    private BigDecimal price;

    @NotNull(groups = ValidationGroup.Create.class)
    @Positive(groups = ValidationGroup.Create.class)
    private Integer maxCapacity;

    @Valid
    @NotNull(groups = ValidationGroup.Create.class)
    @ConvertGroup(from = ValidationGroup.Create.class, to = ValidationGroup.NestedObject.class)
    @JsonDeserialize(converter = NestedConverter.class)
    private HotelDTO hotel;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<BookingRecordDTO> bookings;


    public static class NestedConverter extends StdConverter<JsonNode, HotelDTO> {
        @Override
        public HotelDTO convert(JsonNode value) {
            Long hotelId = null;
            if (value.isObject()) {
                var hotelIdNode = value.get(HotelDTO.Fields.id);
                if (hotelIdNode.isIntegralNumber()) {
                    hotelId = hotelIdNode.asLong();
                }
            } else if (value.isIntegralNumber()) {
                hotelId = value.asLong();
            }

            if (hotelId == null) {
                throw new RuntimeException("Could not parse hotel.id");
            }

            return HotelDTO.builder()
                    .id(hotelId)
                    .build();
        }
    }
}
