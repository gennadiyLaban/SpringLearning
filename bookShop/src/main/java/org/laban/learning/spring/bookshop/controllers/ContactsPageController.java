package org.laban.learning.spring.bookshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ContactsPageController {
    @GetMapping(value = { "/contacts", "/contacts.html" })
    public String contacts() {
        log.info("GET %s".formatted(Pages.CONTACTS.baseUrl));
        return "contacts";
    }
}
