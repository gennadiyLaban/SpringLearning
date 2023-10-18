package org.laban.learning.spring.bookshop.data.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDAO extends JpaRepository<Book, Long> {
}
