package org.laban.learning.spring.lesson3.services;

import lombok.RequiredArgsConstructor;
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
}
