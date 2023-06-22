package org.laban.learning.spring.bookshop.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.services.book.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
@RequiredArgsConstructor
public class MainPageController {
    private static final String VIEW_MAIN = "index";

    @NonNull private final BookService bookService;

    @GetMapping(value = { "/", "/index.html", "/main", "/main/index.html" })
    public String mainPage(Model model) {
        log.info("GET main page");
        model.addAttribute("bookData", bookService.getBookData());
        return VIEW_MAIN;
    }
}
