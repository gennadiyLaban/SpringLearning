package org.laban.learning.spring.bookshop.controllers;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.data.author.AuthorSection;
import org.laban.learning.spring.bookshop.services.author.AuthorsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Slf4j
public class AuthorsPageController {
    private static final String VIEW_AUTHORS = "authors/index";


    private final AuthorsService service;

    public AuthorsPageController(AuthorsService service) {
        this.service = service;
    }

    @GetMapping(value = {"/authors", "/authors/index", "/authors/index.html" })
    public String genres() {
        log.info("GET %s".formatted(Pages.AUTHORS.baseUrl));
        return VIEW_AUTHORS;
    }

    @ModelAttribute("authorsData")
    List<AuthorSection> authorsData() {
        return service.getAuthorsPageData();
    }

}
