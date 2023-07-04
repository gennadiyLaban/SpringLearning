package org.laban.learning.spring.bookshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PopularPageController {
    @GetMapping(value = { "/books/popular", "/books/popular.html" })
    public String popular() {
        log.info("GET %s".formatted(Pages.POPULAR.baseUrl));
        return "/books/popular";
    }
}
