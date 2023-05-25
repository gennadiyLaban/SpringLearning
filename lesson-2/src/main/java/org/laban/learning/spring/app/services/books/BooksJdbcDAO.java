package org.laban.learning.spring.app.services.books;

import lombok.SneakyThrows;
import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.app.services.DAO;
import org.laban.learning.spring.app.services.Qualifiers;
import org.laban.learning.spring.app.services.db.jdbc.*;
import org.laban.learning.spring.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import static org.laban.learning.spring.utils.jdbc.JDBCParams.*;

@Component
@Qualifier(value = Qualifiers.BOOKS)
public class BooksJdbcDAO implements DAO<Book> {
    private final Logger logger = LogFactory.getLogger(BooksJdbcDAO.class);

    private static final String TBL_BOOK = "books";

    private static final String CLN_ID = "id";
    private static final String CLN_AUTHOR = "author";
    private static final String CLN_TITLE = "title";
    private static final String CLN_SIZE = "size";


    private static final String QUERY_RETRIEVE_ALL = "SELECT * FROM %s".formatted(TBL_BOOK);
    private static final String QUERY_RETRIEVE_BOOK_BY_ID = "SELECT * FROM %s WHERE %s=:%s".formatted(
            TBL_BOOK, CLN_ID, paramOf(CLN_ID));
    private static final String QUERY_REMOVE_BOOK_BY_ID = "DELETE FROM %s WHERE %s=:%s".formatted(
            TBL_BOOK, CLN_ID, paramOf(CLN_ID));
    private static final String QUERY_INSERT_BOOK = """
            INSERT INTO %s (%s, %s, %s) VALUES (:%s, :%s, :%s)""".formatted(
                    TBL_BOOK,
            CLN_AUTHOR, CLN_TITLE, CLN_SIZE,
            paramOf(CLN_AUTHOR), paramOf(CLN_TITLE), paramOf(CLN_SIZE)
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final EntityRequester<Book> allBooksRtRetriever;
    private final EntityRequester<Book> bookByIdRetriever;
    private final DBUpdater removeBookByIdUpdater;
    private final DBUpdater insertBookUpdater;

    @Autowired
    public BooksJdbcDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        var idRetriever = bookIdRetriever(logger);
        var authorRetriever = authorRetriever(logger);
        var titleRetriever = titleRetriever(logger);
        var sizeRetriever = sizeRetriever(logger);

        RowMapper<Book> bookMapper = (rs, rowNum) -> Book.builder()
                .id(idRetriever.retrieve(rs, rowNum))
                .author(authorRetriever.retrieve(rs, rowNum))
                .title(titleRetriever.retrieve(rs, rowNum))
                .size(sizeRetriever.retrieve(rs, rowNum))
                .build();

        allBooksRtRetriever = new BaseEntityRequester<>(jdbcTemplate, logger, QUERY_RETRIEVE_ALL, emptyParams(), bookMapper);
        bookByIdRetriever = new BaseEntityRequester<>(jdbcTemplate, logger, QUERY_RETRIEVE_BOOK_BY_ID, paramsOf(CLN_ID), bookMapper);

        removeBookByIdUpdater = new BaseDBUpdater(jdbcTemplate, logger, QUERY_REMOVE_BOOK_BY_ID, paramsOf(CLN_ID));
        insertBookUpdater = new BaseDBUpdater(jdbcTemplate, logger, QUERY_INSERT_BOOK, paramsOf(CLN_AUTHOR, CLN_TITLE, CLN_SIZE));
    }

    @SneakyThrows
    @Override
    public List<Book> retrieveAll() {
        return allBooksRtRetriever.retrieve();
    }

    @SneakyThrows
    @Override
    public Integer store(Book value) {
        return insertBookUpdater.update(value.getAuthor(), value.getTitle(), value.getSize());
    }

    @SneakyThrows
    @Override
    public Book removeItemById(Integer id) {
        var book = onlyOf(bookByIdRetriever.retrieve(id));
        if (book.isPresent()) {
            var updateCount = removeBookByIdUpdater.update(id);
            if (updateCount > 0) {
                return book.get();
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public boolean removeItemByIdQuietly(Integer id) {
        return removeBookByIdUpdater.update(id) > 0;
    }

    private static Retriever<Integer> bookIdRetriever(Logger logger) {
        return new BaseColumnRetriever<>(logger, CLN_ID, (rs, num) -> rs.getInt(CLN_ID));
    }

    private static Retriever<String> authorRetriever(Logger logger) {
        return new BaseColumnRetriever<>(logger, CLN_AUTHOR, (rs, num) -> rs.getString(CLN_AUTHOR));
    }

    private static Retriever<String> titleRetriever(Logger logger) {
        return new BaseColumnRetriever<>(logger, CLN_TITLE, (rs, num) -> rs.getString(CLN_TITLE));
    }

    private static Retriever<Integer> sizeRetriever(Logger logger) {
        return new BaseColumnRetriever<>(logger, CLN_ID, (rs, num) -> rs.getInt(CLN_SIZE));
    }

    private static <T> Optional<T> onlyOf(List<T> container) {
        if (container.size() > 1) {
            throw new IllegalStateException("value in container is not only");
        }

        return container.isEmpty()
                ? Optional.empty()
                : Optional.of(container.get(0));
    }
}
