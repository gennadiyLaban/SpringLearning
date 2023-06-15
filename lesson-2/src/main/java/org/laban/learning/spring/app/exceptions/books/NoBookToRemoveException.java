package org.laban.learning.spring.app.exceptions.books;

import org.laban.learning.spring.app.exceptions.PageException;
import org.laban.learning.spring.web.dto.BookRegexToRemove;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class NoBookToRemoveException extends PageException {
    @lombok.Getter
    @NonNull
    private final BookRegexToRemove dto;
    @lombok.Getter
    @NonNull
    private final BindingResult bindingResult;

    public NoBookToRemoveException(
            @NonNull Model oldModel,
            @NonNull BookRegexToRemove dto,
            @NonNull BindingResult bindingResult
    ) {
        super(oldModel);
        this.dto = dto;
        this.bindingResult = bindingResult;
    }
}
