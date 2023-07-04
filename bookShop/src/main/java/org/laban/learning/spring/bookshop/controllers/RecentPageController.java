package org.laban.learning.spring.bookshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RecentPageController {
    @GetMapping(value = { "/books/recent", "/books/recent.html" })
    public String recent() {
        log.info("GET %s".formatted(Pages.RECENT.baseUrl));
        return "books/recent";
    }

}
