package org.laban.learning.spring.lesson1.contacts.datasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import jakarta.annotation.PostConstruct;
import org.laban.learning.spring.lesson1.config.Profiles;
import org.laban.learning.spring.lesson1.contacts.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Profiles.Init
@Component
public class FileInitContactsDatasource extends BaseContactsDatasource {
    private final String classpath;

    @Autowired
    public FileInitContactsDatasource(
            @Value("${notebook.initial.classpath}") String classpath
    ) {
        this.classpath = classpath;
    }

    @PostConstruct
    void initData() {
        read().forEach(this::addOrReplaceContact);
    }

    private List<Contact> read() {
        var contacts = new ArrayList<Contact>();
        var resource = new ClassPathResource(classpath);
        try (var scanner = new Scanner(resource.getInputStream())) {
            while (scanner.hasNextLine()) {
                var contact = parseContact(scanner.nextLine());
                if (contact != null) {
                    contacts.add(contact);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }

    private Contact parseContact(String line) {
        var split = Arrays.stream(line.split(";"))
                .map(String::trim)
                .toList();
        if (split.size() != 3 || split.stream().anyMatch(String::isBlank)) {
            System.out.println("Contact could not be parsed, " +
                    "because data must consist of non empty name, phone and email that divided by ';', " +
                    "but actual data is '%s'".formatted(line));
            return null;
        }
        return new Contact(
                split.get(0),
                split.get(1),
                split.get(2)
        );
    }
}
