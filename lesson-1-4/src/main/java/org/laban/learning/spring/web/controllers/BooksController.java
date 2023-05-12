package org.laban.learning.spring.web.controllers;

import org.apache.log4j.Logger;
import org.laban.learning.spring.app.services.BookService;
import org.laban.learning.spring.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BooksController {
    private Logger logger = Logger.getLogger(BooksController.class);
    @Autowired
    private BookService bookService;

    @GetMapping("/shelf")
    public String booksShelf(Model model) {
        logger.info("GET books/shelf returns book_shelf.html");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        logger.info("POST books/save with %s".formatted(book));
        if (bookService.saveBook(book)) {
            logger.info("Saving successfull current repository size: %d"
                    .formatted(bookService.getAllBooks().size()));
        } else {
            logger.info("Saving failed");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
        logger.info("POST books/remove with %s".formatted(bookIdToRemove));
        if (bookService.removeBookById(bookIdToRemove)) {
            return "redirect:/books/shelf";
        } else {
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByRegex")
    public String remove(@RequestParam(value = "queryRegex") String queryRegex) {
        logger.info("POST books/removeByRegex with %s".formatted(queryRegex));
        if (bookService.removeBookByRegex(queryRegex)) {
            logger.info("Successful removing with %s".formatted(queryRegex));
            return "redirect:/books/shelf";
        } else {
            logger.info("Failed removing with %s".formatted(queryRegex));
            return "redirect:/books/shelf";
        }
    }
}
