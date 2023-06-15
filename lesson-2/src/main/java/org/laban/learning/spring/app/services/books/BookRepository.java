package org.laban.learning.spring.app.services.books;

import java.util.List;

import org.laban.learning.spring.app.services.DAO;
import org.laban.learning.spring.app.services.ProjectRepository;
import org.laban.learning.spring.app.services.Qualifiers;
import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value= Qualifiers.BOOKS)
public class BookRepository implements ProjectRepository<Book> {
    private final Logger logger = LogFactory.getLogger(BookRepository.class);


    private final DAO<Book> dao;

    public BookRepository(
            @Autowired @Qualifier(value = Qualifiers.BOOKS)
            DAO<Book> dao
    ) {
        this.dao = dao;
    }


    @Override
    public List<Book> retrieveAll() {
        return dao.retrieveAll();
    }

    @Override
    public void store(Book book) {
        logger.info("store new book: %s".formatted(book));
        dao.store(book);
    }

    @Override
    public boolean removeItemById(Integer id) {
        var book = dao.removeItemById(id);
        if (book != null) {
            logger.info("book %s was deleted".formatted(book));
            return true;
        } else {
            logger.info("book with id=%s not found, deleting fail".formatted(id));
            return false;
        }
    }
}
