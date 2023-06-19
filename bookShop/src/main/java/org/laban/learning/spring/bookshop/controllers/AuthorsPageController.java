package org.laban.learning.spring.bookshop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AuthorsPageController {
    private static final String PAGE_AUTHORS = "/authors";
    private static final String PAGE_AUTHORS_INDEX = "/authors/index.html";
    private static final String VIEW_AUTHORS = "authors/index";

    @GetMapping(value = {PAGE_AUTHORS, PAGE_AUTHORS_INDEX})
    public String genres() {
        log.info("GET %s".formatted(PAGE_AUTHORS));
        return VIEW_AUTHORS;
    }

}
