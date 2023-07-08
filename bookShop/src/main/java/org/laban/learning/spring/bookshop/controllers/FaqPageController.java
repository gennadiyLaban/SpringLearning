package org.laban.learning.spring.bookshop.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FaqPageController {
    @GetMapping(value = { "/faq", "/faq.html" })
    public String faq() {
        log.info("GET %s".formatted(Pages.FAQ.baseUrl));
        return "faq";
    }

    @ModelAttribute("navigationPath")
    public Map<Pages, String> navigationPath() {
        var path = new LinkedHashMap<Pages, String>();
        path.put(Pages.MAIN, "str.fragment.breadcrumbs.path.main");
        path.put(Pages.FAQ, "str.fragment.breadcrumbs.path.faq");
        return path;
    }

}
