package org.laban.learning.spring.bookshop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class GenresPageController {
    private static final String VIEW_GENRES = "genres/index";

    @GetMapping(value = { "/genres", "/genres/index.html" })
    public String genres() {
        log.info("GET genres page");
        return VIEW_GENRES;
    }

}
