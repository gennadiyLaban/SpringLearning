package org.laban.learning.spring.bookshop.data.author.jdbc;

import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.data.author.Author;
import org.laban.learning.spring.bookshop.data.author.AuthorsDAO;
import org.laban.learning.spring.util.jdbc.EntityRequester;
import org.laban.learning.spring.util.jdbc.JDBCParams;
import org.laban.learning.spring.util.jdbc.Retrievers;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Lazy
@Component
@Slf4j
public class AuthorsJdbcDAO implements AuthorsDAO {
    private final EntityRequester<Author> allAuthorsRetriever;
    private final EntityRequester<Author> authorByIdRetriever;

    public AuthorsJdbcDAO(
            AuthorsTable authorsTable,
            NamedParameterJdbcTemplate jdbcTemplate
    ) {
        var idRetriever = Retrievers.intColumnOf(log, authorsTable.id);
        var firstNameRetriever = Retrievers.strColumnOf(log, authorsTable.first_name);
        var lastNameRetriever = Retrievers.strColumnOf(log, authorsTable.last_name);

        RowMapper<Author> authorMapper = (rs, rowNum) -> Author.builder()
                .id(idRetriever.retrieve(rs, rowNum))
                .firstName(firstNameRetriever.retrieve(rs, rowNum))
                .lastName(lastNameRetriever.retrieve(rs, rowNum))
                .build();

        final var queryRetrieveAll = "SELECT * FROM %s".formatted(authorsTable.compositeTblName());
        this.allAuthorsRetriever = Retrievers.entityOf(jdbcTemplate, log, queryRetrieveAll, authorMapper);

        final var queryFindAuthorById = "SELECT * FROM %s as %s"
                .formatted(authorsTable.compositeTblName(), authorsTable.aliasTblName())
                + " "
                + "WHERE %s.%s=:%s"
                .formatted(
                        authorsTable.aliasTblName(),
                        authorsTable.plainId(),
                        JDBCParams.paramOf(authorsTable.plainId()));
        this.authorByIdRetriever = Retrievers.entityOf(
                jdbcTemplate,
                log,
                queryFindAuthorById,
                authorMapper,
                JDBCParams.paramOf(authorsTable.plainId())
        );
    }

    @SneakyThrows
    @Override
    public List<Author> retrieveAll() {
        return allAuthorsRetriever.retrieve();
    }

    @SneakyThrows
    @Override
    public Author findAuthorById(Integer id) {
        var list = authorByIdRetriever.retrieve(id);
        return list.size() > 0 ? list.get(0) : null;
    }
}
