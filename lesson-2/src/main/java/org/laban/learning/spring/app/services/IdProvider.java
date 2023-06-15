package org.laban.learning.spring.app.services;

import org.laban.learning.spring.web.dto.Book;
import org.springframework.lang.NonNull;

public interface IdProvider {
    @NonNull
    String provideId(@NonNull Book book);
}
