package org.laban.learning.spring.lessonfinal.repository;

import org.laban.learning.spring.lessonfinal.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
