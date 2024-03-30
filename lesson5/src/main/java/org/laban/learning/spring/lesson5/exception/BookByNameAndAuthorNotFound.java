package org.laban.learning.spring.lesson5.exception;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookByNameAndAuthorNotFound extends RuntimeException {
    @Nonnull
    private final String name;
    @Nonnull
    private final String author;
}
