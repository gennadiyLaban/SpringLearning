package org.laban.learning.spring.lesson1.commands;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson1.Printer;
import org.laban.learning.spring.lesson1.contacts.ContactsDatasource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PrintAllCommandHandler implements CommandsHandler {
    private static final Set<String> SUPPORTED_COMMANDS = Set.of("printAll");

    private final ContactsDatasource contactsDatasource;
    private final Printer printer;

    @Override
    public Set<String> supportedCommands() {
        return SUPPORTED_COMMANDS;
    }

    @Override
    public void handle(String commandBody) {
        var contacts = contactsDatasource.getAllContacts();
        printer.divider();
        if (contacts.isEmpty()) {
            printer.border().print("No contacts yet....").newLine();
        } else {
            for (var contact : contacts) {
                printer.border().contact(contact).newLine();
            }
        }
        printer.divider();
    }
}
