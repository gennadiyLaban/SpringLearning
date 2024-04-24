package org.laban.learning.spring.lessonfinal.repository;

import org.laban.learning.spring.lessonfinal.model.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
}
