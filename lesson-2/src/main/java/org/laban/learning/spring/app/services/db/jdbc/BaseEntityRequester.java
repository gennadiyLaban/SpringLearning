package org.laban.learning.spring.app.services.db.jdbc;

import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.utils.jdbc.JDBCParams;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.laban.learning.spring.utils.jdbc.JDBCParams.configureParamSource;
import static org.laban.learning.spring.utils.jdbc.JDBCParams.emptyParamSource;

public class BaseEntityRequester<T> implements EntityRequester<T> {
    protected final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger logger;
    protected final String queryTemplate;
    private final String[] paramNames;
    protected final RowMapper<T> rowMapper;

    public BaseEntityRequester(
            NamedParameterJdbcTemplate jdbcTemplate,
            Logger logger,
            String queryTemplate,
            String[] paramNames,
            RowMapper<T> rowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.logger = logger;
        this.queryTemplate = queryTemplate;
        this.paramNames = paramNames;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<T> retrieve() {
        logger.debug(formatExecStartingMsg(queryTemplate));
        try {
            return jdbcTemplate.query(queryTemplate, emptyParamSource(), rowMapper);
        } catch (Throwable th) {
            logger.error(formatExecErrorMsg(queryTemplate, th));
            throw th;
        }
    }

    @Override
    public List<T> retrieve(Object... args) {
        logger.debug(formatExecStartingMsg(queryTemplate, args));
        try {
            return jdbcTemplate.query(queryTemplate, configureParamSource(paramNames, args), rowMapper);
        } catch (Throwable th) {
            logger.error(formatExecErrorMsg(queryTemplate, th, args));
            throw th;
        }
    }

    private String formatExecStartingMsg(String query, Object ... args) {
        return "execute \"%s\" with params %s".formatted(query, Arrays.toString(args));
    }

    private String formatExecErrorMsg(String query, Object ... args) {
        return "executing error: \"%s\" with params %s".formatted(query, Arrays.toString(args));
    }
}
