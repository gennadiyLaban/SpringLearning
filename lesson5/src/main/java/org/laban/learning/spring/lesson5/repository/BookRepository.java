package org.laban.learning.spring.lesson5.repository;

import jakarta.annotation.Nonnull;
import org.laban.learning.spring.lesson5.model.Book;
import org.laban.learning.spring.lesson5.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Nonnull
    Optional<Book> findByNameAndAuthor(@Nonnull String name, @Nonnull String author);

    @Nonnull
    List<Book> findBooksByCategory(@Nonnull Category category);
}
