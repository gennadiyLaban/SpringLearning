package org.laban.learning.spring.app.exceptions.books;

import org.laban.learning.spring.app.exceptions.PageException;
import org.laban.learning.spring.web.dto.BookRegexToRemove;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class RemoveBookByRegexValidationException extends PageException {
    @lombok.Getter
    @NonNull
    private final BindingResult bindingResult;

    @lombok.Getter
    @NonNull
    private final BookRegexToRemove dto;

    public RemoveBookByRegexValidationException(
            @NonNull BindingResult bindingResult,
            @NonNull Model oldModel,
            @NonNull BookRegexToRemove dto
    ) {
        super(oldModel);
        this.bindingResult = bindingResult;
        this.dto = dto;
    }
}
