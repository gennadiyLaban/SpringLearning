package org.laban.learning.spring.bookshop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class GenresPageController {
    private static final String PAGE_GENRES = "/genres";
    private static final String PAGE_GENRES_INDEX = "/genres/index.html";
    private static final String VIEW_GENRES = "genres/index";

    @GetMapping(value = { PAGE_GENRES, PAGE_GENRES_INDEX })
    public String genres() {
        log.info("GET %s".formatted(PAGE_GENRES));
        return VIEW_GENRES;
    }

}
