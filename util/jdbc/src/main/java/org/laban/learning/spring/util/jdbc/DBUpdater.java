package org.laban.learning.spring.util.jdbc;

public interface DBUpdater {
    int update();
    int update(Object ... args);
    int[] batchUpdate(Object[] ... args);
}
