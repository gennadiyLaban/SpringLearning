package org.laban.learning.spring.lesson2.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StudentNotFoundError extends RuntimeException {
    private final Long id;
}
