package org.laban.learning.spring.app.exceptions.books;

import org.laban.learning.spring.app.exceptions.PageException;
import org.laban.learning.spring.web.dto.UploadBookFile;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class UploadBookFileValidationException extends PageException {
    @lombok.Getter
    @NonNull
    private final BindingResult bindingResult;

    @lombok.Getter
    @NonNull
    private final UploadBookFile dto;

    public UploadBookFileValidationException(
            @NonNull BindingResult bindingResult,
            @NonNull Model oldModel,
            @NonNull UploadBookFile dto
    ) {
        super(oldModel);
        this.bindingResult = bindingResult;
        this.dto = dto;
    }
}
