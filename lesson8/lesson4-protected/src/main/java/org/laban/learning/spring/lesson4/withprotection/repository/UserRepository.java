package org.laban.learning.spring.lesson4.withprotection.repository;

import org.laban.learning.spring.lesson4.withprotection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
