package org.laban.learning.spring.app.services.db.jdbc;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Retriever<T> {
    T retrieve(@NotNull ResultSet rs, int rowNum) throws SQLException;
}
