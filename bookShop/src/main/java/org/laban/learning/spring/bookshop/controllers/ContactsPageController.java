package org.laban.learning.spring.bookshop.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ContactsPageController {
    @GetMapping(value = { "/contacts", "/contacts.html" })
    public String contacts() {
        log.info("GET %s".formatted(Pages.CONTACTS.baseUrl));
        return "contacts";
    }

    @ModelAttribute("navigationPath")
    public Map<Pages, String> navigationPath() {
        var path = new LinkedHashMap<Pages, String>();
        path.put(Pages.MAIN, "str.fragment.breadcrumbs.path.main");
        path.put(Pages.CONTACTS, "str.fragment.breadcrumbs.path.contacts");
        return path;
    }
}
