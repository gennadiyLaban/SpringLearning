package org.laban.learning.spring.bookshop.data.author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsDAO extends JpaRepository<Author, Long> {
}
