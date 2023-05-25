package org.laban.learning.spring.app.services.db.jdbc;

public interface DBUpdater {
    int update();
    int update(Object ... args);
    int[] batchUpdate(Object[] ... args);
}
