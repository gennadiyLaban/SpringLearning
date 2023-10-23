package org.laban.learning.spring.lesson1.commands;

import java.util.Set;

public interface CommandsHandler {
    Set<String> supportedCommands();

    void handle(String commandBody);

}
