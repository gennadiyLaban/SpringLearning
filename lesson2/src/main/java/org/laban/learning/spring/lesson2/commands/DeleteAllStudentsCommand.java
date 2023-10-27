package org.laban.learning.spring.lesson2.commands;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson2.MessageBuilder;
import org.laban.learning.spring.lesson2.students.datasource.StudentsDatasource;
import org.springframework.shell.command.annotation.Command;

@RequiredArgsConstructor
@Command
public class DeleteAllStudentsCommand {
    private final StudentsDatasource datasource;

    @Command(command = "deleteAll", alias = { "deleteall" })
    public String deleteAll() {
        var students = datasource.getAllStudents();
        var builder = new MessageBuilder();
        for (var student : students) {
            datasource.delete(student.getId());
        }
        builder.divider();
        builder.border().formatMsg("{0} students were deleted", students.size()).newLine();
        builder.divider();
        return builder.toString();
    }
}
