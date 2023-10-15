package org.laban.learning.spring.bookshop.data.author;

import java.util.List;

import org.springframework.lang.Nullable;

public interface AuthorsDAO {
    List<Author> retrieveAll();

    @Nullable
    Author findAuthorById(Integer id);
}
