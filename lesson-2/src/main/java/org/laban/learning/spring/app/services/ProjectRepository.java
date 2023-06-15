package org.laban.learning.spring.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retrieveAll();
    void store(T value);

    boolean removeItemById(Integer id);
}
