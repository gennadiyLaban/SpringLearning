package org.laban.learning.spring.lesson1.exceptions.input;

public class CommandNotFoundException extends IllegalInputError {
    @Override
    public String userMessage() {
        return "Unknown command, please check!";
    }
}
