package org.laban.learning.spring.app.services.db.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.validation.constraints.NotNull;

@FunctionalInterface
public interface Retriever<T> {
    T retrieve(@NotNull ResultSet rs, int rowNum) throws SQLException;
}
