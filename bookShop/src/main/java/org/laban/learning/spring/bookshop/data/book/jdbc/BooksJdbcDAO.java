package org.laban.learning.spring.bookshop.data.book.jdbc;

import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.data.author.jdbc.AuthorsTable;
import org.laban.learning.spring.bookshop.data.book.Book;
import org.laban.learning.spring.bookshop.data.book.BookDAO;
import org.laban.learning.spring.util.jdbc.EntityRequester;
import org.laban.learning.spring.util.jdbc.Retrievers;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BooksJdbcDAO implements BookDAO {
    private final EntityRequester<Book> allBooksRetriever;

    public BooksJdbcDAO(BooksTable booksTable, AuthorsTable authorsTable, NamedParameterJdbcTemplate jdbcTemplate) {
        final var queryRetrieveAll = "SELECT %s as %s, %s as %s, %s as %s, %s as %s, %s as %s, %s as %s\n".formatted(
                booksTable.compositeId(), booksTable.aliasId(),
                authorsTable.compositeFirstName(), authorsTable.aliasFirstName(),
                authorsTable.compositeLastName(), authorsTable.aliasLastName(),
                booksTable.compositeTitle(), booksTable.aliasTitle(),
                booksTable.compositePriceOld(), booksTable.aliasPriceOld(),
                booksTable.compositePrice(), booksTable.aliasPrice()
        ) + "FROM %s\n ".formatted(
                booksTable.compositeTblName()
        ) + "INNER JOIN %s ON %s=%s;".formatted(
                authorsTable.compositeTblName(),
                booksTable.compositeAuthorId(), authorsTable.compositeId()
        );

        var idRetriever = Retrievers.intColumnOf(log, booksTable.aliasId());
        var authorRetriever = Retrievers.compositeOf(
                Retrievers.strColumnOf(log, authorsTable.aliasFirstName()),
                Retrievers.strColumnOf(log, authorsTable.aliasLastName()),
                "%s %s"::formatted
        );
        var titleRetriever = Retrievers.strColumnOf(log, booksTable.aliasTitle());
        var priceOldRetriever = Retrievers.strColumnOf(log, booksTable.aliasPriceOld());
        var priceRetriever = Retrievers.strColumnOf(log, booksTable.aliasPrice());

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
