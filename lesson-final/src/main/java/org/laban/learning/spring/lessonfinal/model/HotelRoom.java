package org.laban.learning.spring.lessonfinal.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.StdConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.laban.learning.spring.lessonfinal.web.dto.hotel.HotelDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Table(
        indexes = @Index(name = "rooms_hotel_id_index", columnList = "hotel_id"),
        uniqueConstraints = @UniqueConstraint(
                name = "UK_rooms_hotel_id_room_number",
                columnNames = {"hotel_id", "room_number"}
        )
)
@Entity(name = "rooms")
public class HotelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "room_number")
    private Integer roomNumber;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer maxCapacity;

    @With
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, referencedColumnName = "id")
    private Hotel hotel;

    @OrderBy("startDate ASC")
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings = new ArrayList<>();


    public static class NestedConverter extends StdConverter<JsonNode, HotelDTO> {
        @Override
        public HotelDTO convert(JsonNode value) {
            if (value == null) {
                return null;
            }

            Long hotelId = null;
            if (value.isObject()) {
                var hotelIdNode = value.get(HotelDTO.Fields.id);
                if (hotelIdNode != null && hotelIdNode.isIntegralNumber()) {
                    hotelId = hotelIdNode.asLong();
                }
            } else if (value.isIntegralNumber()) {
                hotelId = value.asLong();
            }

            return HotelDTO.builder()
                    .id(hotelId)
                    .build();
        }
    }
}
