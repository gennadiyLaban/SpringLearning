package org.laban.learning.spring.lesson2.commands;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson2.MessageBuilder;
import org.laban.learning.spring.lesson2.students.datasource.StudentsDatasource;
import org.springframework.shell.command.annotation.Command;

@RequiredArgsConstructor
@Command
public class PrintAllCommand {
    private final StudentsDatasource datasource;

    @Command(command = "printAll", alias = { "printall" })
    public String printAll() {
        var students = datasource.getAllStudents();
        var builder = new MessageBuilder();
        builder.divider();
        if (students.isEmpty()) {
            builder.border().msg("No students yet...").newLine();
        } else {
            for (var student : students) {
                builder.border().student(student).newLine();
            }
        }
        builder.divider();
        return builder.toString();
    }
}
