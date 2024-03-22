package org.laban.learning.spring.lesson4.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class CategoryNotFoundException extends RuntimeException {
    private final Long categoryId;

    public CategoryNotFoundException(Long categoryId) {
        this.categoryId = categoryId;
    }
}
