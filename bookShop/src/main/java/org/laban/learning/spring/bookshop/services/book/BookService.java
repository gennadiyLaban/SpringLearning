package org.laban.learning.spring.bookshop.services.book;

import java.util.List;

import org.laban.learning.spring.bookshop.data.book.Book;
import org.laban.learning.spring.bookshop.data.book.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getBookData() {
        return repository.getAllBooks();
    }
}
