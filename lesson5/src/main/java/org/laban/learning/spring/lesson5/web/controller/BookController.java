package org.laban.learning.spring.lesson5.web.controller;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson5.service.BookService;
import org.laban.learning.spring.lesson5.web.dto.BookDTO;
import org.laban.learning.spring.lesson5.web.dto.BookListDTO;
import org.laban.learning.spring.lesson5.web.validation.group.ValidationGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RequestMapping("api/v1/book")
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable @Nonnull Long id) {
        return ResponseEntity.ok(bookService.findBookDtoById(id));
    }

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

    @PostMapping
    public ResponseEntity<Void> createBook(
            @RequestBody @Validated(ValidationGroup.Create.class) BookDTO request,
            UriComponentsBuilder builder
    ) {
        return ResponseEntity.created(builder.path("/book").path("/{id}").buildAndExpand(
                bookService.createBookByDto(request)
        ).toUri()).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateBook(
            @RequestBody @Validated(ValidationGroup.Update.class)
            BookDTO request
    ) {
        bookService.updateBookByDto(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable @Nonnull Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
