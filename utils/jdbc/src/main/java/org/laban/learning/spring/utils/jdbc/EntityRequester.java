package org.laban.learning.spring.utils.jdbc;

import java.util.List;

public interface EntityRequester<T> {
    List<T> retrieve() throws Throwable;

    List<T> retrieve(Object... args) throws Throwable;
}
