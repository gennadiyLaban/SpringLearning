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


    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (handleNextCommand(scanner));
    }

    public boolean handleNextCommand(Scanner scanner) {
        try {
            System.out.print("Input command: ");
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
            printDivider();
            printErrorMessage(e.userMessage());
            printDivider();
            printNewLine();
            return true;
        } catch (Exception e) {
            printDivider();
            System.out.printf("== Unexpected exception: %s\n", e.getMessage());
            e.printStackTrace();
            printDivider();
            printNewLine();
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

    private void printDivider() {
        System.out.println("==================================================================");
    }

    private void printNewLine() {
        System.out.println();
    }

    private void printErrorMessage(String message) {
        System.out.printf("== Input Error: %s\n", message);
    }

    private record Command(@Nonnull String key, @Nullable String body) {

    }

}
