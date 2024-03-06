package org.laban.learning.spring.lesson3.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson3.services.ContactsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ContactsController {
    private final ContactsService contactsService;

    @GetMapping("/")
    public String contacts(Model model) {
        model.addAttribute("contactList", contactsService.findAllContacts());
        return "contacts";
    }

}
