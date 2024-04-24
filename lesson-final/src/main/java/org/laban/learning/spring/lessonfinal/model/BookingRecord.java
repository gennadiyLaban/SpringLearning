package org.laban.learning.spring.lessonfinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;

@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Table(indexes = @Index(
        name = "bookings_room_id_start_index",
        columnList = "room_id, start ASC"
))
@Entity(name = "bookings")
public class BookingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant start;

    @Column(nullable = false)
    private Instant end;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, referencedColumnName = "id")
    private HotelRoom room;
}
