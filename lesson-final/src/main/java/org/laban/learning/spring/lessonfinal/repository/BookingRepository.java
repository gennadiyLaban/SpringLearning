package org.laban.learning.spring.lessonfinal.repository;

import org.laban.learning.spring.lessonfinal.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    default Boolean isBooked(Booking booking) {
        return isBooked(booking.getRoom().getId(), booking.getStartDate(), booking.getEndDate());
    }

    @Query(nativeQuery = true, value = "SELECT isBooked(:roomId, :startDate, :endDate)")
    Boolean isBooked(
            @Param("roomId") Long roomId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );
}
