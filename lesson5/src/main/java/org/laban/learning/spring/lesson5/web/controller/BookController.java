package org.laban.learning.spring.lesson5.web.controller;

import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson5.model.Book;
import org.laban.learning.spring.lesson5.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/book")
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/list")
    public ResponseEntity<List<Book>> bookList() {
        return ResponseEntity.ok(bookService.findAll());
    }
}
