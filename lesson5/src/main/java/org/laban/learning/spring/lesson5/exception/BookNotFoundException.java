package org.laban.learning.spring.lesson5.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookNotFoundException extends RuntimeException {
    private final Long bookId;
}
