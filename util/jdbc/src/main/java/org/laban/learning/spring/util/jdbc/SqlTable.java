package org.laban.learning.spring.util.jdbc;

import java.util.Arrays;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public abstract class SqlTable {
    @Nullable
    private final String schema;
    @NonNull
    private final String tblName;

    public SqlTable(@Nullable String schema, @NonNull String tblName) {
        this.schema = schema;
        this.tblName = tblName;
    }

    public SqlTable(@NonNull String tblName) {
        this(null, tblName);
    }

    public String compositeTblName() {
        return "%s.%s".formatted(schema, tblName);
    }

    public String aliasTblName() {
        return compositeTblName().replace('.', '_');
    }

    public String compositeColumn(@NonNull String clnName) {
        return "%s.%s".formatted(compositeTblName(), clnName);
    }

    public String columnAlias(@NonNull String clnName) {
        return compositeColumn(clnName).replace('.', '_');
    }


    public String[] compositeColumn(@NonNull String... clnNames) {
        return Arrays.stream(clnNames)
                .map(this::compositeColumn)
                .toArray(String[]::new);
    }
}
