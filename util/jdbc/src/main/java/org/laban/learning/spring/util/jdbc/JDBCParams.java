package org.laban.learning.spring.util.jdbc;

import java.util.HashMap;

import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class JDBCParams {
    private JDBCParams() {}

    public static String paramOf(String columnName) {
        return "%s_param".formatted(columnName);
    }

    public static String[] paramsOf(String ... columnNames) {
        var params = new String[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            params[i] = paramOf(columnNames[i]);
        }
        return params;
    }

    public static String[] emptyParams() {
        return new String[0];
    }

    public static SqlParameterSource configureParamSource(String[] paramNames, Object[] args) {
        if (paramNames.length != args.length) {
            throw new RuntimeException("Size of param names and args must be equal");
        }

        final var map = new HashMap<String, Object>();
        for(int index = 0; index < paramNames.length; index++) {
            map.put(paramNames[index], args[index]);
        }
        if (map.size() != paramNames.length) {
            throw new RuntimeException("Param names must be unique");
        }
        return new MapSqlParameterSource(map);
    }

    public static SqlParameterSource emptyParamSource() {
        return EmptySqlParameterSource.INSTANCE;
    }
}
