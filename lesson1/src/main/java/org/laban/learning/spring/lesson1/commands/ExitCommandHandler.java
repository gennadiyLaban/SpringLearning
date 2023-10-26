package org.laban.learning.spring.lesson1.commands;

import java.util.Set;

import org.laban.learning.spring.lesson1.exceptions.ExitCommandException;
import org.springframework.stereotype.Component;

@Component
public class ExitCommandHandler implements CommandsHandler {
    private static final Set<String> SUPPORTED_COMMANDS = Set.of("exit");

    @Override
    public Set<String> supportedCommands() {
        return SUPPORTED_COMMANDS;
    }

    @Override
    public void handle(String commandBody) {
        throw new ExitCommandException();
    }
}
