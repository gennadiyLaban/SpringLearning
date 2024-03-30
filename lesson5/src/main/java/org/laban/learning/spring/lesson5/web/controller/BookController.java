package org.laban.learning.spring.lesson5.web.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson5.service.BookService;
import org.laban.learning.spring.lesson5.web.dto.BookDTO;
import org.laban.learning.spring.lesson5.web.dto.BookListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1/book")
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/search")
    public ResponseEntity<BookDTO> findByNameAndAuthor(
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String author
    ) {
        return ResponseEntity.ok(bookService.findDtoByNameAndAuthor(name, author));
    }

    @GetMapping("/list/category")
    public ResponseEntity<BookListDTO> findByNameAndAuthor(
            @RequestParam @NotBlank String categoryName
    ) {
        return ResponseEntity.ok(bookService.findAllDtoByCategory(categoryName));
    }


}
