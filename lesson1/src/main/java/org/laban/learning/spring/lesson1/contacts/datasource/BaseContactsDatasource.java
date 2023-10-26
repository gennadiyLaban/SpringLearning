package org.laban.learning.spring.lesson1.contacts.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.laban.learning.spring.lesson1.contacts.Contact;
import org.laban.learning.spring.lesson1.contacts.ContactsDatasource;

public abstract class BaseContactsDatasource implements ContactsDatasource {
    protected final Map<String, Contact> contacts = new HashMap<>();

    @Override
    public List<Contact> getAllContacts() {
        return contacts.values().stream().toList();
    }

    @Override
    public Contact addOrReplaceContact(Contact contact) {
        contacts.put(contact.getEmail(), contact);
        return contact;
    }

    @Override
    public Contact findContactByEmail(String email) {
        return contacts.get(email);
    }

    @Override
    public Contact deleteContactByEmail(String email) {
        return contacts.remove(email);
    }
}
