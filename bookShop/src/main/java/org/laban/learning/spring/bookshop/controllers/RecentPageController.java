package org.laban.learning.spring.bookshop.controllers;

import java.util.List;

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
public class RecentPageController {
    private final BookService bookService;

    @GetMapping(value = { "/books/recent", "/books/recent.html" })
    public String recent() {
        log.info("GET %s".formatted(Pages.RECENT.baseUrl));
        return "books/recent";
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.getBooksData();
    }

}
