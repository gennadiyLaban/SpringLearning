package org.laban.learning.spring.lesson2.commands;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson2.MessageBuilder;
import org.laban.learning.spring.lesson2.students.Student;
import org.laban.learning.spring.lesson2.students.datasource.StudentsDatasource;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@RequiredArgsConstructor
@Command
public class CreateCommand {
    private final StudentsDatasource datasource;

    @Command(command = "create")
    public String createStudent(
            @NotEmpty(message = "first name must not be empty")
            @Option(required = true)
            String firstName,
            @NotEmpty(message = "last name must not be emtpy")
            @Option(required = true)
            String lastName,
            @NotNull
            @Min(value = 1, message = "age must be more than 0")
            @Max(value = 99, message = "age must be less than 100")
            @Option(required = true)
            Integer age
    ) {
        var student = datasource.save(Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build());
        var builder = new MessageBuilder();
        builder.divider();
        builder.border().msg("Student ").student(student).msg(" was saved").newLine();
        builder.divider();
        return builder.toString();
    }
}
