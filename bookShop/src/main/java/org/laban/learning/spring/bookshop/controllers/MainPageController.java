package org.laban.learning.spring.bookshop.controllers;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laban.learning.spring.bookshop.data.book.Book;
import org.laban.learning.spring.bookshop.services.book.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
@Slf4j
@RequiredArgsConstructor
public class MainPageController {
    private static final String VIEW_MAIN = "index";

    @NonNull private final BookService bookService;

    @GetMapping(value = { "/", "/index.html", "/main", "/main/index.html" })
    public String mainPage() {
        return VIEW_MAIN;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getBooksData();
    }
}
