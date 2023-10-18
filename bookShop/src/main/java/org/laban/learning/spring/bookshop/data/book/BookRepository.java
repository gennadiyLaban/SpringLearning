package org.laban.learning.spring.bookshop.data.book;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    private final BookDAO dao;

    public BookRepository(BookDAO dao) {
        this.dao = dao;
    }

    public List<Book> getAllBooks() {
        return dao.findAll();
    }
}
