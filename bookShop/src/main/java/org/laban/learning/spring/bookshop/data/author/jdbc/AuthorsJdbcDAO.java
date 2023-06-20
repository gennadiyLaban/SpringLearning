package org.laban.learning.spring.bookshop.data.author.jdbc;

import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.data.author.Author;
import org.laban.learning.spring.bookshop.data.author.AuthorsDAO;
import org.laban.learning.spring.utils.jdbc.EntityRequester;
import org.laban.learning.spring.utils.jdbc.Retrievers;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorsJdbcDAO implements AuthorsDAO {
    private final EntityRequester<Author> allAuthorsRetriever;

    public AuthorsJdbcDAO(AuthorsTable authorsTable, NamedParameterJdbcTemplate jdbcTemplate) {
        final var queryRetrieveAll = "SELECT * FROM %s".formatted(authorsTable.tblName);

        var idRetriever = Retrievers.intColumnOf(log, authorsTable.id);
        var nameRetriever = Retrievers.strColumnOf(log, authorsTable.name);

        RowMapper<Author> authorMapper = (rs, rowNum) -> Author.builder()
                .id(idRetriever.retrieve(rs, rowNum))
                .name(nameRetriever.retrieve(rs, rowNum))
                .build();

        this.allAuthorsRetriever = Retrievers.entityOf(jdbcTemplate, log, queryRetrieveAll, authorMapper);
    }

    @SneakyThrows
    @Override
    public List<Author> retrieveAll() {
        return allAuthorsRetriever.retrieve();
    }
}
