package org.laban.learning.spring.utils.jdbc;

public interface DBUpdater {
    int update();
    int update(Object ... args);
    int[] batchUpdate(Object[] ... args);
}
