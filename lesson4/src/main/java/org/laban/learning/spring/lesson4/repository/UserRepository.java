package org.laban.learning.spring.lesson4.repository;

import org.laban.learning.spring.lesson4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
