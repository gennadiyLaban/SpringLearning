package org.laban.learning.spring.lesson5.service;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson5.model.Book;
import org.laban.learning.spring.lesson5.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

}
