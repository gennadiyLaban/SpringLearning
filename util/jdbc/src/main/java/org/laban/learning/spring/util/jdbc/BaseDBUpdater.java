package org.laban.learning.spring.util.jdbc;

import java.util.Arrays;

import org.slf4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class BaseDBUpdater implements DBUpdater {
    protected final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger logger;
    protected final String queryTemplate;
    private final String[] paramNames;

    public BaseDBUpdater(
            NamedParameterJdbcTemplate jdbcTemplate,
            Logger logger,
            String queryTemplate,
            String[] paramNames
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.logger = logger;
        this.queryTemplate = queryTemplate;
        this.paramNames = paramNames;
    }

    @Override
    public int update() {
        logger.debug(formatExecStartingMsg(queryTemplate));
        try {
            return jdbcTemplate.update(queryTemplate, JDBCParams.emptyParamSource());
        } catch (Throwable th) {
            logger.error(formatExecErrorMsg(queryTemplate, th));
            throw th;
        }
    }

    @Override
    public int update(Object... args) {
        logger.debug(formatExecStartingMsg(queryTemplate));
        try {
            return jdbcTemplate.update(queryTemplate, JDBCParams.configureParamSource(paramNames, args));
        } catch (Throwable th) {
            logger.error(formatExecErrorMsg(queryTemplate, th));
            throw th;
        }
    }

    @Override
    public int[] batchUpdate(Object[]... args) {
        logger.info(formatExecStartingMsg(queryTemplate));
        try {
            var batchParams = new SqlParameterSource[args.length];
            for (int index = 0; index < args.length; index++) {
                batchParams[index] = JDBCParams.configureParamSource(paramNames, args[index]);
            }
            return jdbcTemplate.batchUpdate(queryTemplate, batchParams);
        } catch (Throwable th) {
            logger.error(formatExecErrorMsg(queryTemplate, th));
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
