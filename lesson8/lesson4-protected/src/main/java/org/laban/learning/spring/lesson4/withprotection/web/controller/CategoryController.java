package org.laban.learning.spring.lesson4.withprotection.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.service.CategoryService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.category.CategoryDTO;
import org.laban.learning.spring.lesson4.withprotection.web.dto.category.CategoryLIstDTO;
import org.laban.learning.spring.lesson4.withprotection.web.validation.group.ValidationGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> categoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryDTOById(id));
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
        var createdId = categoryService.createCategoryByDTO(categoryDTO);
        return ResponseEntity.created(
                builder.path("/category").path("/{id}").buildAndExpand(createdId).toUri()
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
}
