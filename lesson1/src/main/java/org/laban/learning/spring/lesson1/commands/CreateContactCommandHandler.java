package org.laban.learning.spring.lesson1.commands;

import java.util.Arrays;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson1.Printer;
import org.laban.learning.spring.lesson1.contacts.Contact;
import org.laban.learning.spring.lesson1.contacts.ContactsDatasource;
import org.laban.learning.spring.lesson1.exceptions.input.SimpleInputError;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateContactCommandHandler implements CommandsHandler {
    private static final Set<String> SUPPORTED_COMMANDS = Set.of("create");

    private final ContactsDatasource contactsDatasource;
    private final Printer printer;

    @Override
    public Set<String> supportedCommands() {
        return SUPPORTED_COMMANDS;
    }

    @Override
    public void handle(String commandBody) {
        if (StringUtils.isBlank(commandBody)) {
            throw new SimpleInputError("Contact data is empty!");
        }
        var inputParts = Arrays.stream(commandBody.split(";"))
                .map(String::trim)
                .toList();
        if (inputParts.size() != 3 || inputParts.stream().anyMatch(String::isBlank)) {
            throw new SimpleInputError(
                    "Contact data must consist of non empty name, phone and email that divided by ';'");
        }

        var contact = contactsDatasource.addOrReplaceContact(new Contact(
                inputParts.get(0),
                inputParts.get(1),
                inputParts.get(2)
        ));

        printer
                .divider()
                .border().print("Contact '").contact(contact).print("'").newLine()
                .border().print("Was saved successfully").newLine()
                .divider();
    }
}
