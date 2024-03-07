package org.laban.learning.spring.lesson3.data;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class InMemoryContactsDatasource implements ContactsDatasource {
    private static long ID_BASE = 0;

    private final List<Contact> contactList = new ArrayList<>();

    @PostConstruct
    protected void initializeDefaultContacts() {
        contactList.add(Contact.builder()
                .id(++ID_BASE)
                .firstName("Gennadiy")
                .lastName("Laban")
                .email("tagudur@yandex.ru")
                .phone("+375336020092")
                .build());
        contactList.add(Contact.builder()
                .id(++ID_BASE)
                .firstName("Nataliya")
                .lastName("Laban")
                .email("nataliya@yandex.ru")
                .phone("+375336859977")
                .build());
    }

    @Override
    public List<Contact> contacts() {
        return List.copyOf(contactList);
    }

    @Override
    public void addContact(Contact contact) {
        contactList.add(contact.withId(++ID_BASE));
    }
}