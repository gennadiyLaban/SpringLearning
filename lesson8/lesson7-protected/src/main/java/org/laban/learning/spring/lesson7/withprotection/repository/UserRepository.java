package org.laban.learning.spring.lesson7.withprotection.repository;

import jakarta.annotation.Nonnull;
import org.laban.learning.spring.lesson7.withprotection.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findUserByName(@Nonnull String name);
}
