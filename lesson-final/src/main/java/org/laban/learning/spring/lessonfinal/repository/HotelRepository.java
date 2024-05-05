package org.laban.learning.spring.lessonfinal.repository;

import jakarta.annotation.Nonnull;
import org.laban.learning.spring.lessonfinal.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query(nativeQuery = true, value = "SELECT isHotelAlreadyMarked(:hotelId, :userId)")
    Boolean isHotelAlreadyMarked(@Nonnull Long hotelId, @Nonnull Long userId);

    @Query(nativeQuery = true, value = "SELECT saveHotelRating(:hotelId, :userId)")
    void saveHotelRating(@Nonnull Long hotelId, @Nonnull Long userId);
}
