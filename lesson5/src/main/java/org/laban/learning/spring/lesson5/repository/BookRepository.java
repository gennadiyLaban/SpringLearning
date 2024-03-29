package org.laban.learning.spring.lesson5.repository;

import org.laban.learning.spring.lesson5.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
