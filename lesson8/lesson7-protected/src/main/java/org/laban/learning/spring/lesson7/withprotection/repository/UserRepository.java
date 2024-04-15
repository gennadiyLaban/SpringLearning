package org.laban.learning.spring.lesson7.withprotection.repository;

import org.laban.learning.spring.lesson7.withprotection.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
