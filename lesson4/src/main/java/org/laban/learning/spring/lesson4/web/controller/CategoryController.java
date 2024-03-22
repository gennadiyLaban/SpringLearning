package org.laban.learning.spring.lesson4.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.exception.CategoryNotFoundException;
import org.laban.learning.spring.lesson4.service.CategoryService;
import org.laban.learning.spring.lesson4.utils.ErrorResponseUtils;
import org.laban.learning.spring.lesson4.web.dto.ErrorBodyDTO;
import org.laban.learning.spring.lesson4.web.dto.category.CategoryDTO;
import org.laban.learning.spring.lesson4.web.dto.category.CategoryLIstDTO;
import org.laban.learning.spring.lesson4.web.validation.group.ValidationGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.MessageFormat;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> categoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findCategoryDTOById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<CategoryLIstDTO> categoryList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size
    ) {
        return ResponseEntity.ok(
                categoryService.findAllByDTO(Pageable.ofSize(size).withPage(page))
        );
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(
            @RequestBody @Valid CategoryDTO categoryDTO,
            UriComponentsBuilder builder
    ) {
        var category = categoryService.createCategoryByDTO(categoryDTO);
        return ResponseEntity.created(
                builder.path("/category").path("/{id}").buildAndExpand(category.getId()).toUri()
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable Long id,
            @RequestBody
            @Validated(value = {ValidationGroup.Update.class})
            CategoryDTO categoryDTO
    ) {
        categoryService.updateCategoryByDTO(categoryDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    public ResponseEntity<ErrorBodyDTO> categoryNotFoundException(
            CategoryNotFoundException exception,
            HttpServletRequest request
    ) {
        return ErrorResponseUtils.buildError(HttpStatus.NOT_FOUND, request,
                MessageFormat.format("Category with id={0} not found!", exception.getCategoryId()));
    }
}
