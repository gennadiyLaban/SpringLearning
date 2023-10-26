package org.laban.learning.spring.lesson1.contacts;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface ContactsDatasource {
    @Nonnull
    List<Contact> getAllContacts();
    Contact addOrReplaceContact(Contact contact);
    @Nullable
    Contact findContactByEmail(@Nonnull String email);
    @Nullable
    Contact deleteContactByEmail(@Nonnull String email);
}
