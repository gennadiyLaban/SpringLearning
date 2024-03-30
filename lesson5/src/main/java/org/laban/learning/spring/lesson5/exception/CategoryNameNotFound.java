package org.laban.learning.spring.lesson5.exception;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryNameNotFound extends RuntimeException {
    @Nonnull
    private final String categoryName;
}
