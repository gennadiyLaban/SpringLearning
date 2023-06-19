package org.laban.learning.spring.bookshop.data.book.jdbc;

import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.data.book.Book;
import org.laban.learning.spring.bookshop.data.book.BookDAO;
import org.laban.learning.spring.utils.jdbc.EntityRequester;
import org.laban.learning.spring.utils.jdbc.Retrievers;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BooksJdbcDAO implements BookDAO {
    private static final String TBL_BOOKS = "books";

    private static final String CLN_ID = "id";
    private static final String CLN_AUTHOR = "author";
    private static final String CLN_TITLE = "title";
    private static final String CLN_PRICE_OLD = "priceOld";
    private static final String CLN_PRICE = "price";

    private static final String QUERY_RETRIEVE_ALL = "SELECT * FROM %s".formatted(TBL_BOOKS);

    private final EntityRequester<Book> allBooksRetriever;

    public BooksJdbcDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        var idRetriever = Retrievers.intColumnOf(log, CLN_ID);
        var authorRetriever = Retrievers.strColumnOf(log, CLN_AUTHOR);
        var titleRetriever = Retrievers.strColumnOf(log, CLN_TITLE);
        var priceOldRetriever = Retrievers.strColumnOf(log, CLN_PRICE_OLD);
        var priceRetriever = Retrievers.strColumnOf(log, CLN_PRICE);

        RowMapper<Book> bookMapper = (rs, rowNum) -> Book.builder()
                .id(idRetriever.retrieve(rs, rowNum))
                .author(authorRetriever.retrieve(rs, rowNum))
                .title(titleRetriever.retrieve(rs, rowNum))
                .priceOld(priceOldRetriever.retrieve(rs, rowNum))
                .price(priceRetriever.retrieve(rs, rowNum))
                .build();

        allBooksRetriever = Retrievers.entityOf(jdbcTemplate, log, QUERY_RETRIEVE_ALL, bookMapper);
    }

    @SneakyThrows
    @Override
    public List<Book> retrieveAll() {
        return allBooksRetriever.retrieve();
    }

}
