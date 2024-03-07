package org.laban.learning.spring.lesson3.services;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson3.controller.ContactSample;
import org.laban.learning.spring.lesson3.data.Contact;
import org.laban.learning.spring.lesson3.data.ContactsDatasource;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactsService {
    private final ContactsDatasource contactsDatasource;

    public List<Contact> findAllContacts() {
        return contactsDatasource.contacts();
    }

    public void addContact(ContactSample contactSample) {
        contactsDatasource.addContact(Contact
                        .builder()
                        .firstName(contactSample.getFirstName())
                        .lastName(contactSample.getLastName())
                        .phone(contactSample.getPhone())
                        .email(contactSample.getEmail())
                        .build());
    }
}