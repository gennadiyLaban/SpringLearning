package org.laban.learning.spring.app.exceptions;

import org.springframework.lang.NonNull;

public class DomainException extends RuntimeException {
    @NonNull
    protected final String message;

    public DomainException(@NonNull String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String getMessage() {
        return message;
    }
}
