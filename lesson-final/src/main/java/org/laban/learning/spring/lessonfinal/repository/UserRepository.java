package org.laban.learning.spring.lessonfinal.repository;

import org.laban.learning.spring.lessonfinal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
