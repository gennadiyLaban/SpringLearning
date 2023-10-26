package org.laban.learning.spring.lesson1.exceptions.input;

public abstract class IllegalInputError extends RuntimeException {
    public abstract String userMessage();
}
