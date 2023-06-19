package org.laban.learning.spring.bookshop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.services.book.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
public class MainPageController {
    private static final String PAGE_MAIN = "/main";
    private static final String PAGE_MAIN_INDEX = "/main/index.html";
    private static final String PAGE_MAIN_ROOT = "/";
    private static final String PAGE_MAIN_ROOT_INDEX = "/index.html";
    private static final String VIEW_MAIN = "index";

    private final BookService bookService;

    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = { PAGE_MAIN, PAGE_MAIN_INDEX, PAGE_MAIN_ROOT, PAGE_MAIN_ROOT_INDEX })
    public String mainPage(Model model) {
        log.info("GET %s".formatted(PAGE_MAIN));
        model.addAttribute("bookData", bookService.getBookData());
        return VIEW_MAIN;
    }
}
