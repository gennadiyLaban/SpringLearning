package org.laban.learning.spring.lesson1;

import java.util.Scanner;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.laban.learning.spring.lesson1.commands.CommandInterpreter;
import org.laban.learning.spring.lesson1.exceptions.ExitCommandException;
import org.laban.learning.spring.lesson1.exceptions.input.IllegalInputError;
import org.laban.learning.spring.lesson1.exceptions.input.SimpleInputError;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ContactsNotebook {
    private final CommandInterpreter commandInterpreter;
    private final Printer printer;


    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (handleNextCommand(scanner));
    }

    public boolean handleNextCommand(Scanner scanner) {
        try {
            printer.newLine();
            printer.print("Input command: ");
            var input = scanner.nextLine();
            if (StringUtils.isBlank(input)) {
                return true;
            }

            var command = parseUserInput(input);
            var commandHandler = commandInterpreter.interpret(command.key());
            if (commandHandler == null) {
                throw new SimpleInputError("Unknown command, please retry");
            }

            commandHandler.handle(command.body());
            return true;
        } catch (ExitCommandException e) {
            return false;
        } catch (IllegalInputError e) {
            printer
                    .divider()
                    .border().print("Input Error: ").print(e.userMessage()).newLine()
                    .divider();
            return true;
        } catch (Exception e) {
            printer
                    .divider()
                    .border().print("Unexpected exception: ").print(e.getMessage()).newLine();
            printer.divider();
            return true;
        }
    }

    private Command parseUserInput(String input) {
        var spaceIndex = input.indexOf(' ');

        if (spaceIndex == -1) {
            return new Command(input, null);
        }

        return new Command(input.substring(0, spaceIndex), input.substring(spaceIndex + 1));
    }

    private record Command(@Nonnull String key, @Nullable String body) {

    }

}
