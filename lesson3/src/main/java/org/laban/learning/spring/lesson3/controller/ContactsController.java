package org.laban.learning.spring.lesson3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson3.services.ContactsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ContactsController {
    private final ContactsService contactsService;

    @GetMapping("/")
    public String contacts(Model model) {
        model.addAttribute("contactList", contactsService.findAllContacts());
        return "contacts";
    }

    @GetMapping("/addcontact")
    public String addContact(Model model) {
        return "addcontact";
    }

    @PostMapping("/addcontact")
    public String createContact(@Valid ContactSample sample) {
        contactsService.addContact(sample);
        return "redirect:/";
    }

    @ModelAttribute("contactSample")
    public ContactSample contactSample() {
        return new ContactSample();
    }

}