package org.laban.learning.spring.lesson5.repository;

import jakarta.annotation.Nonnull;
import org.laban.learning.spring.lesson5.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByName(@Nonnull String name);
}
