package org.laban.learning.spring.util.jdbc;

import java.util.Arrays;

import org.springframework.lang.NonNull;

public abstract class SqlTable {
    @NonNull
    public final String tblName;

    public SqlTable(@NonNull String tblName) {
        this.tblName = tblName;
    }

    public String compositeColumn(@NonNull String clnName) {
        return "%s.%s".formatted(tblName, clnName);
    }


    public String[] compositeColumn(@NonNull String... clnNames) {
        return Arrays.stream(clnNames)
                .map(this::compositeColumn)
                .toArray(String[]::new);
    }
}
