package org.laban.learning.spring.bookshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DocumentsPageController {
    @GetMapping(value = { "/documents", "/documents/index", "/documents/index.html" })
    public String documents() {
        log.info("GET %s".formatted(Pages.DOCUMENTS.baseUrl));
        return "/documents/index";
    }
}
