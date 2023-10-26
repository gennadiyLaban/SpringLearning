package org.laban.learning.spring.lesson1.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.laban.learning.spring.lesson1.exceptions.input.CommandNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CommandInterpreter {
    private final Map<String, CommandsHandler> commandHandlers = new HashMap<>();

    public CommandInterpreter(
            List<CommandsHandler> handlers
    ) {
        fillCommands(handlers);
    }

    private void fillCommands(List<CommandsHandler> handlers) {
        for (var handler: handlers) {
            for (var command: handler.supportedCommands()) {
                var commandToFind = command.trim().toLowerCase(Locale.ROOT);
                if (commandHandlers.containsKey(commandToFind)) {
                    throw new IllegalStateException("Command %s is already presented".formatted(commandToFind));
                }
                commandHandlers.put(commandToFind.toLowerCase(), handler);
            }
        }
    }

    public CommandsHandler interpret(String input) {
        var command = input.trim().toLowerCase(Locale.ROOT);
        var commandHandler = commandHandlers.get(command);
        if (commandHandler == null) {
            throw new CommandNotFoundException();
        }
        return commandHandler;
    }

}
