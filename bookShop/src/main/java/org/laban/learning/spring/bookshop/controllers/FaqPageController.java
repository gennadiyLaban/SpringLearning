package org.laban.learning.spring.bookshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FaqPageController {
    @GetMapping(value = { "/faq", "/faq.html" })
    public String faq() {
        log.info("GET %s".formatted(Pages.FAQ.baseUrl));
        return "faq";
    }

}
