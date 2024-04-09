package org.laban.learning.spring.lesson7.exception;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserNotFoundException extends RuntimeException {
    @NonNull
    private final String userId;
}
