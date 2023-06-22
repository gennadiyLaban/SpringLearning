package org.laban.learning.spring.utils.jdbc;

import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class Retrievers {
    private Retrievers() {}

    public static Retriever<String> strColumnOf(Logger logger, String columnName) {
        return new BaseColumnRetriever<>(logger, columnName, (rs, num) -> rs.getString(columnName));
    }

    public static Retriever<Integer> intColumnOf(Logger logger, String columnName) {
        return new BaseColumnRetriever<Integer>(logger, columnName, ((rs, rowNum) -> rs.getInt(columnName)));
    }

    public static <T> EntityRequester<T> entityOf(
            NamedParameterJdbcTemplate jdbcTemplate,
            Logger logger,
            String query,
            RowMapper<T> rowMapper,
            String... paramNames
    ) {
        return new BaseEntityRequester<>(jdbcTemplate, logger, query, paramNames, rowMapper);
    }
}
