package org.laban.learning.spring.bookshop.data.book.jdbc;

import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.data.author.jdbc.AuthorsTable;
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
    private final EntityRequester<Book> allBooksRetriever;

    public BooksJdbcDAO(BooksTable booksTable, AuthorsTable authorsTable, NamedParameterJdbcTemplate jdbcTemplate) {
        final var queryRetrieveAll = "SELECT %s, %s, %s, %s, %s\n".formatted(
                booksTable.compositeId(),
                authorsTable.compositeName(),
                booksTable.compositeTitle(),
                booksTable.compositePriceOld(),
                booksTable.compositePrice()
        ) + "FROM %s\n".formatted(
                booksTable.tblName
        ) + "INNER JOIN %s ON %s=%s;".formatted(
                authorsTable.tblName, booksTable.compositeAuthorId(), authorsTable.compositeId()
        );

        var idRetriever = Retrievers.intColumnOf(log, booksTable.compositeId());
        var authorRetriever = Retrievers.strColumnOf(log, authorsTable.compositeName());
        var titleRetriever = Retrievers.strColumnOf(log, booksTable.compositeTitle());
        var priceOldRetriever = Retrievers.strColumnOf(log, booksTable.compositePriceOld());
        var priceRetriever = Retrievers.strColumnOf(log, booksTable.compositePrice());

        RowMapper<Book> bookMapper = (rs, rowNum) -> Book.builder()
                .id(idRetriever.retrieve(rs, rowNum))
                .author(authorRetriever.retrieve(rs, rowNum))
                .title(titleRetriever.retrieve(rs, rowNum))
                .priceOld(priceOldRetriever.retrieve(rs, rowNum))
                .price(priceRetriever.retrieve(rs, rowNum))
                .build();

        allBooksRetriever = Retrievers.entityOf(jdbcTemplate, log, queryRetrieveAll, bookMapper);
    }

    @SneakyThrows
    @Override
    public List<Book> retrieveAll() {
        return allBooksRetriever.retrieve();
    }

}
