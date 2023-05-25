package org.laban.learning.spring.app.services;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DAO<T> {
    @NotNull
    List<T> retrieveAll();

    Integer store(@NotNull T value);

    @Nullable
    T removeItemById(Integer id);

    @Nullable
    boolean removeItemByIdQuietly(Integer id);
}
