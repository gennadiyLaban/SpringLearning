package org.laban.learning.spring.lesson3.data;

import java.util.List;

public interface ContactsDatasource {
    List<Contact> contacts();

    void addContact(Contact contact);
}