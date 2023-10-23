package org.laban.learning.spring.lesson1.exceptions.input;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleInputError extends IllegalInputError {
    private final String userMassage;

    @Override
    public String userMessage() {
        return userMassage;
    }
}
