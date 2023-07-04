package org.laban.learning.spring.util.jdbc;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class Retrievers {
    private Retrievers() {}

    public static <T1, T2, R> Retriever<R> compositeOf(
            Retriever<T1> firstRetriever,
            Retriever<T2> secondRetriever,
            Function2<T1, T2, R> mapper
    ) {
        return ((rs, rowNum) -> mapper.invoke(
                firstRetriever.retrieve(rs, rowNum),
                secondRetriever.retrieve(rs, rowNum)
        )) ;
    }

    public static <T1, T2, T3, R> Retriever<R> compositeOf(
            Retriever<T1> firstRetriever,
            Retriever<T2> secondRetriever,
            Retriever<T3> thirdRetriever,
            Function3<T1, T2, T3, R> mapper
    ) {
        return ((rs, rowNum) -> mapper.invoke(
                firstRetriever.retrieve(rs, rowNum),
                secondRetriever.retrieve(rs, rowNum),
                thirdRetriever.retrieve(rs, rowNum)
        )) ;
    }

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
