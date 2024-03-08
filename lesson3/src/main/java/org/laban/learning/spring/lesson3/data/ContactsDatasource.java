package org.laban.learning.spring.lesson3.data;

import java.util.List;

public interface ContactsDatasource {
    List<Contact> contacts();
    Contact findContactById(long id);
    void addContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContactById(long id);
}