package org.laban.learning.spring.app.exceptions;

import org.springframework.lang.NonNull;
import org.springframework.ui.Model;

public abstract class PageException extends RuntimeException {

    @lombok.Getter
    @NonNull
    private final Model oldModel;

    protected PageException(@NonNull Model oldModel) {
        this.oldModel = oldModel;
    }
}
