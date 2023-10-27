package org.laban.learning.spring.lesson2.commands;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson2.MessageBuilder;
import org.laban.learning.spring.lesson2.exceptions.StudentNotFoundError;
import org.laban.learning.spring.lesson2.students.datasource.StudentsDatasource;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.command.annotation.Option;

@RequiredArgsConstructor
@Command
public class DeleteCommand {
    private final StudentsDatasource datasource;

    @Command(command = "delete")
    public String createStudent(
            @NotNull(message = "id must not be null")
            @Min(value = 1, message = "id must not be more than 0")
            @Option(required = true)
            Long id
    ) {
        var student = datasource.delete(id);
        var builder = new MessageBuilder();
        builder.divider();
        builder.border().msg("Student ").student(student).msg(" was deleted").newLine();
        builder.divider();
        return builder.toString();
    }

    @ExceptionResolver
    public CommandHandlingResult handle(StudentNotFoundError error) {
        var builder = new MessageBuilder();
        builder.divider();
        builder.border()
                .formatMsg("Student wit id:{0} not found and couldn''t be deleted", error.getId()).newLine();
        builder.divider();
        return CommandHandlingResult.of(builder.toString());
    }
}
