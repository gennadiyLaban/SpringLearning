package org.laban.learning.spring.lesson2.commands;

import java.text.MessageFormat;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson2.students.datasource.StudentsDatasource;
import org.springframework.shell.command.annotation.Command;

@RequiredArgsConstructor
@Command
public class PrintAllCommand {
    private final StudentsDatasource datasource;

    @Command(command = "printAll", alias = { "printall" })
    public String printAll() {
        var builder = new StringBuilder();
        datasource.getAllStudents().stream()
                .map(student -> MessageFormat.format(
                        "{0} | {1} | {2} | {3}",
                        student.getId(), student.getFirstName(), student.getLastName(), student.getAge()
                ))
                .forEach(message -> builder.append(message).append('\n'));
        return builder.toString();
    }
}
