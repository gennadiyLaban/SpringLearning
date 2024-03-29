package org.laban.learning.spring.bookshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SearchPageController {
    @GetMapping(value = { "/search", "/search/index", "/search/index.html" } )
    public String search() {
        log.info("GET %s".formatted(Pages.SEARCH.baseUrl));
        return "search/index";
    }
}
