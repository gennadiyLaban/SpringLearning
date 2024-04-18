package org.laban.learning.spring.lesson4.withprotection.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.service.CategoryService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.category.CategoryDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.category.CategoryLIstDTO;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.MessageFormat;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> categoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryDTOById(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<CategoryLIstDTO> categoryList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2147483647") Integer size
    ) {
        return ResponseEntity.ok(
                categoryService.findAllByDTO(Pageable.ofSize(size).withPage(page))
        );
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createCategory(
            @RequestBody @Valid CategoryDTO categoryDTO,
            UriComponentsBuilder builder
    ) {
        var createdId = categoryService.createCategoryByDTO(categoryDTO);
        return ResponseEntity.created(
                URI.create(MessageFormat.format("/api/v1/category/{0}", createdId))
        ).build();
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
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

    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
