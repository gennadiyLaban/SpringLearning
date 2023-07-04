package org.laban.learning.spring.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.lang.NonNull;

@FunctionalInterface
public interface Retriever<T> {
    T retrieve(@NonNull ResultSet rs, int rowNum) throws SQLException;
}
