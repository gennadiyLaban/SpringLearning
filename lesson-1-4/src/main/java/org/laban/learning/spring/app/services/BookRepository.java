package org.laban.learning.spring.app.services;

import org.apache.log4j.Logger;
import org.laban.learning.spring.web.dto.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Qualifier(value=Qualifiers.BOOKS)
public class BookRepository implements ProjectRepostitory<Book> {
    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();


    @Override
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId((long) book.hashCode());
        logger.info("store new book: %s".formatted(book));
        repo.add(book);
    }

    @Override
    public boolean removeItemById(long bookIdToRemove) {
        var iterator = repo.listIterator();
        while (iterator.hasNext()) {
            var book = iterator.next();
            if (bookIdToRemove == book.getId()) {
                iterator.remove();
                logger.info("book %s was deleted".formatted(book));
                return true;
            }
        }
        logger.info("book with id=%d not found, deleting fail".formatted(bookIdToRemove));
        return false;
    }
}
