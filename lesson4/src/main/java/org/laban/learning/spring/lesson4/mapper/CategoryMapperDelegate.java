package org.laban.learning.spring.lesson4.mapper;

import jakarta.annotation.Nullable;
import org.laban.learning.spring.lesson4.model.Category;
import org.laban.learning.spring.lesson4.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collections;
import java.util.List;

public abstract class CategoryMapperDelegate implements CategoryMapper {
    @Lazy
    @Autowired
    private CategoryService categoryService;

    @Override
    public Category categoryIdToCategory(@Nullable Long categoryId) {
        if (categoryId == null) {
            return null;
        }

        return categoryService.getCategoryById(categoryId);
    }

    @Override
    public List<Category> categoryIdsToCategoryList(@Nullable List<Long> ids) {
        if (ids == null) {
            return null;
        }
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        return categoryService.getAllByIds(ids);
    }
}
