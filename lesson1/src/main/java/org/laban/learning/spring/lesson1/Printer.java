package org.laban.learning.spring.lesson1;

import java.io.PrintStream;

import org.laban.learning.spring.lesson1.contacts.Contact;
import org.springframework.stereotype.Component;

@Component
public class Printer {
    private final PrintStream printer = System.out;

    public Printer print(String message) {
        printer.print(message);
        return this;
    }

    public Printer newLine() {
        printer.println();
        return this;
    }

    public Printer divider() {
        printer.println("==================================================================");
        return this;
    }

    public Printer border() {
        printer.print("== ");
        return this;
    }

    public Printer contact(Contact contact) {
        print(contact.getName())
                .print(" | ").print(contact.getPhone())
                .print(" | ").print(contact.getEmail());
        return this;
    }

}
