package org.laban.learning.spring.lesson7.withprotection.repository;

import org.laban.learning.spring.lesson7.withprotection.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
