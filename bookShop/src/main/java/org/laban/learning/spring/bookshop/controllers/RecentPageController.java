package org.laban.learning.spring.bookshop.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @ModelAttribute("navigationPath")
    public Map<Pages, String> navigationPath() {
        var path = new LinkedHashMap<Pages, String>();
        path.put(Pages.MAIN, "str.fragment.breadcrumbs.path.main");
        path.put(Pages.GENRES, "str.fragment.breadcrumbs.path.genres");
        path.put(Pages.RECENT, "str.fragment.breadcrumbs.path.recent");
        return path;
    }

}
