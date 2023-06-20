package org.laban.learning.spring.bookshop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.services.author.AuthorsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AuthorsPageController {
    private static final String PAGE_AUTHORS = "/authors";
    private static final String PAGE_AUTHORS_INDEX = "/authors/index.html";
    private static final String VIEW_AUTHORS = "authors/index";

    private static final String MODEL_ATTRIBUTE_AUTHORS_DATA = "authorsData";


    private final AuthorsService service;

    public AuthorsPageController(AuthorsService service) {
        this.service = service;
    }

    @GetMapping(value = {PAGE_AUTHORS, PAGE_AUTHORS_INDEX})
    public String genres(Model model) {
        log.info("GET %s".formatted(PAGE_AUTHORS));
        var authorsList = service.getAuthorsPageData();
        log.info("GET %s, first: %s".formatted(PAGE_AUTHORS, authorsList.get(0)));
        model.addAttribute(MODEL_ATTRIBUTE_AUTHORS_DATA, authorsList);
        return VIEW_AUTHORS;
    }

}
