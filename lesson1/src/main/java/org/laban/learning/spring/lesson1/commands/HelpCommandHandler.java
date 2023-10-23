package org.laban.learning.spring.lesson1.commands;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson1.Printer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HelpCommandHandler implements CommandsHandler {
    public static final String HELP_COMMAND = "help";
    private static final Set<String> SUPPORTED_COMMANDS = Set.of(HELP_COMMAND);

    private final Printer printer;
    @Lazy
    @Autowired
    private List<CommandsHandler> handlers;

    @Override
    public Set<String> supportedCommands() {
        return SUPPORTED_COMMANDS;
    }

    @Override
    public void handle(String commandBody) {
        printer.divider();
        printer.border().print("Now supported next commands: ").print(HELP_COMMAND);
        handlers.stream()
                .flatMap(handler -> handler.supportedCommands().stream())
                .filter(command -> !HELP_COMMAND.equals(command))
                .forEach(command -> printer.print(", ").print(command));
        printer.newLine();
        printer.divider();
    }
}
