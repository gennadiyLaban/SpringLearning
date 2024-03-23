package org.laban.learning.spring.lesson4.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class CategoryNotFoundException extends RuntimeException {
    private final Long categoryId;
    private final List<Long> categoryIds;

    public CategoryNotFoundException(Long categoryId) {
        this.categoryId = categoryId;
        this.categoryIds = null;
    }

    public CategoryNotFoundException(List<Long> categoryIds) {
        this.categoryId = null;
        this.categoryIds = categoryIds;
    }
}
