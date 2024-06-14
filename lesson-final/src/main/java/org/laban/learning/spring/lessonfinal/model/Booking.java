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
@Table(indexes = {
        @Index(name = "bookings_room_id_start_date_index", columnList = "room_id, start_date ASC"),
        @Index(name = "bookings_user_id_start_date_index", columnList = "user_id, start_date ASC")
})
@Entity(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, referencedColumnName = "id")
    private HotelRoom room;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, referencedColumnName = "id")
    private User user;
}
