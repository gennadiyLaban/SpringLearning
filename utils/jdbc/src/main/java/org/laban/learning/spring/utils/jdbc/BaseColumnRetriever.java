package org.laban.learning.spring.utils.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class BaseColumnRetriever<T> implements Retriever<T> {
    private final Logger logger;
    private final String columnName;
    private final Integer columnNumber;
    private final RowMapper<T> mapper;

    public BaseColumnRetriever(
            Logger logger,
            String columnName,
            RowMapper<T> mapper
    ) {
        this.logger = logger;
        this.columnName = columnName;
        this.mapper = mapper;
        this.columnNumber = null;
    }

    public BaseColumnRetriever(
            Logger logger,
            Integer columnNumber,
            RowMapper<T> mapper
    ) {
        this.logger = logger;
        this.columnNumber = columnNumber;
        this.mapper = mapper;
        this.columnName = null;
    }

    @Override
    public T retrieve(@NonNull ResultSet rs, int rowNum) throws SQLException {
        try {
            return mapper.mapRow(rs, rowNum);
        } catch (Throwable th) {
            logger.error(formatExecErrorMessage(), th);
            throw th;
        }
    }

    private String formatExecErrorMessage() {
        return "error retrieve %s".formatted(columnNumber != null ? String.valueOf(columnNumber) :columnName);
    }
}
