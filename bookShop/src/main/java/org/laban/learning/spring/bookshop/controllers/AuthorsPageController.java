package org.laban.learning.spring.bookshop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.services.author.AuthorsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AuthorsPageController {
    private static final String VIEW_AUTHORS = "authors/index";

    private static final String MODEL_ATTRIBUTE_AUTHORS_DATA = "authorsData";


    private final AuthorsService service;

    public AuthorsPageController(AuthorsService service) {
        this.service = service;
    }

    @GetMapping(value = {"/authors", "/authors/index.html"})
    public String genres(Model model) {
        log.info("GET authors page");
        var authorsList = service.getAuthorsPageData();
        model.addAttribute(MODEL_ATTRIBUTE_AUTHORS_DATA, authorsList);
        return VIEW_AUTHORS;
    }

}
