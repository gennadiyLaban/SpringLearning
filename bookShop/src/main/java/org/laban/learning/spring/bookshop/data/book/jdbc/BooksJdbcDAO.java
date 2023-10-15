package org.laban.learning.spring.bookshop.data.book.jdbc;

import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.data.author.AuthorsDAO;
import org.laban.learning.spring.bookshop.data.book.Book;
import org.laban.learning.spring.bookshop.data.book.BookDAO;
import org.laban.learning.spring.util.jdbc.EntityRequester;
import org.laban.learning.spring.util.jdbc.Retrievers;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Lazy
@Component
@Slf4j
public class BooksJdbcDAO implements BookDAO {
    private final EntityRequester<Book> allBooksRetriever;

    public BooksJdbcDAO(
            BooksTable booksTable,
            AuthorsDAO authorsDAO,
            NamedParameterJdbcTemplate jdbcTemplate
    ) {
        final var queryRetrieveAll = "SELECT %s as %s, %s as %s, %s as %s, %s as %s, %s as %s\n".formatted(
                booksTable.compositeId(), booksTable.aliasId(),
                booksTable.compositeTitle(), booksTable.aliasTitle(),
                booksTable.compositePriceOld(), booksTable.aliasPriceOld(),
                booksTable.compositePrice(), booksTable.aliasPrice(),
                booksTable.compositeAuthorId(), booksTable.aliasAuthorId()
        ) + "FROM %s\n ".formatted(
                booksTable.compositeTblName()
        );

        var idRetriever = Retrievers.intColumnOf(log, booksTable.aliasId());
        var authorIdRetriever = Retrievers.intColumnOf(log, booksTable.aliasAuthorId());
        var titleRetriever = Retrievers.strColumnOf(log, booksTable.aliasTitle());
        var priceOldRetriever = Retrievers.strColumnOf(log, booksTable.aliasPriceOld());
        var priceRetriever = Retrievers.strColumnOf(log, booksTable.aliasPrice());

        RowMapper<Book> bookMapper = (rs, rowNum) -> Book.builder()
                .id(idRetriever.retrieve(rs, rowNum))
                .author(authorsDAO.findAuthorById(authorIdRetriever.retrieve(rs, rowNum)))
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
