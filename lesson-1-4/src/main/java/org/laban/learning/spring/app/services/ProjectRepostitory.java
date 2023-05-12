package org.laban.learning.spring.app.services;

import java.util.List;

public interface ProjectRepostitory<T> {
    List<T> retrieveAll();
    void store(T value);

    boolean removeItemById(long bookIdToRemove);
}
