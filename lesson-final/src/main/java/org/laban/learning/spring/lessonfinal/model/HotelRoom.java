package org.laban.learning.spring.lessonfinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

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

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, referencedColumnName = "id")
    private Hotel hotel;

    @OrderBy("start ASC")
    @OneToMany(mappedBy = "room")
    private List<BookingRecord> bookings = new ArrayList<>();
}
