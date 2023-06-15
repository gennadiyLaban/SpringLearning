package org.laban.learning.spring.app.services;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

public interface DAO<T> {
    @NotNull
    List<T> retrieveAll();

    Integer store(@NotNull T value);

    @Nullable
    T removeItemById(Integer id);

    @Nullable
    boolean removeItemByIdQuietly(Integer id);
}
