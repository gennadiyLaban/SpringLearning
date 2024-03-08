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

    public Contact findContactById(long id) {
        return contactsDatasource.findContactById(id);
    }

    public void addContact(ContactSample sample) {
        contactsDatasource.addContact(createContactFromSample(sample));
    }

    public void deleteContactById(long id) {
        contactsDatasource.deleteContactById(id);
    }

    public void updateContact(ContactSample sample) {
        var contact = contactsDatasource.findContactById(sample.getId());
        if (contact == null) {
            return;
        }
        contactsDatasource.updateContact(createContactFromSample(sample));
    }

    private Contact createContactFromSample(ContactSample sample) {
        return Contact
                .builder()
                .id(sample.getId())
                .firstName(sample.getFirstName())
                .lastName(sample.getLastName())
                .phone(sample.getPhone())
                .email(sample.getEmail())
                .build();
    }
}