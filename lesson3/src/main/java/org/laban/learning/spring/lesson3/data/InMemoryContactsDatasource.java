package org.laban.learning.spring.lesson3.data;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryContactsDatasource implements ContactsDatasource {
    private static long ID_BASE = 0;

    private final Map<Long, Contact> contacts = new HashMap<>();

    @PostConstruct
    protected void initializeDefaultContacts() {
        contacts.put(++ID_BASE, Contact.builder()
                .id(ID_BASE)
                .firstName("Gennadiy")
                .lastName("Laban")
                .email("tagudur@yandex.ru")
                .phone("+375336020092")
                .build());
        contacts.put(++ID_BASE, Contact.builder()
                .id(ID_BASE)
                .firstName("Nataliya")
                .lastName("Laban")
                .email("nataliya@yandex.ru")
                .phone("+375336859977")
                .build());
    }

    @Override
    public List<Contact> contacts() {
        return List.copyOf(contacts.values());
    }

    @Override
    public Contact findContactById(long id) {
        return contacts.get(id);
    }

    @Override
    public void addContact(Contact contact) {
        contacts.put(++ID_BASE, contact.withId(ID_BASE));
    }

    @Override
    public void updateContact(Contact contact) {
        contacts.put(contact.getId(), contact);
    }

    @Override
    public void deleteContactById(long id) {
        contacts.remove(id);
    }
}