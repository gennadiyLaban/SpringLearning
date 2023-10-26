package org.laban.learning.spring.lesson1.commands;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson1.Printer;
import org.laban.learning.spring.lesson1.contacts.ContactsDatasource;
import org.laban.learning.spring.lesson1.exceptions.input.SimpleInputError;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteCommandHandler implements CommandsHandler {
    private static final Set<String> SUPPORTED_COMMANDS = Set.of("delete");

    private final ContactsDatasource contactsDatasource;
    private final Printer printer;

    @Override
    public Set<String> supportedCommands() {
        return SUPPORTED_COMMANDS;
    }

    @Override
    public void handle(String commandBody) {
        var email = commandBody.trim();
        if (StringUtils.isBlank(email)) {
            throw new SimpleInputError("Contact email must not be empty!");
        }

        var contact = contactsDatasource.deleteContactByEmail(email);
        printer.divider();
        if (contact == null) {
            printer.border().print("Contact with email '").print(email).print("' was not found").newLine();
        } else {
            printer.border().print("Contact '").contact(contact).print("' was deleted successfully").newLine();
        }
        printer.divider();
    }
}
