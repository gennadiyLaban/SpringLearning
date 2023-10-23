package org.laban.learning.spring.lesson1.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
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
public class SaveToFileCommandHandler implements CommandsHandler {
    private static final Set<String> SUPPORTED_COMMANDS = Set.of("saveToFile");

    private final ContactsDatasource contactsDatasource;
    private final Printer printer;

    @Override
    public Set<String> supportedCommands() {
        return SUPPORTED_COMMANDS;
    }

    @Override
    public void handle(String commandBody) {
        if (StringUtils.isBlank(commandBody)) {
            throw new SimpleInputError("Path must not be empty!");
        }
        var strPath = commandBody.trim();

        var contacts = contactsDatasource.getAllContacts();
        saveToFile(strPath, contacts);

        printer.divider();
        printer.border().print("Contacts was saved successfully").newLine();
        printer.divider();
    }

    private void saveToFile(String strPath, List<Contact> contacts) {
        var path = Path.of(strPath);
        try(var out = new PrintWriter(Files.newOutputStream(path,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE
        ))) {
            contacts.forEach(contact -> {
                out.print(contact.getName());
                out.print(';');
                out.print(contact.getPhone());
                out.print(';');
                out.print(contact.getEmail());
                out.println();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
